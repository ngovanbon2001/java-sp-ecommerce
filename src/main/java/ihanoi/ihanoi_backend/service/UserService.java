package ihanoi.ihanoi_backend.service;

import ihanoi.ihanoi_backend.bo.AdminUserDetails;
import ihanoi.ihanoi_backend.common.Const;
import ihanoi.ihanoi_backend.dto.user.*;
import ihanoi.ihanoi_backend.entity.File;
import ihanoi.ihanoi_backend.entity.User;
import ihanoi.ihanoi_backend.exception.BizException;
import ihanoi.ihanoi_backend.mapper.UserMapper;
import ihanoi.ihanoi_backend.repository.UserRepository;
import ihanoi.ihanoi_backend.repository.WardRepository;
import ihanoi.ihanoi_backend.repository.specifications.UserSpecification;
import ihanoi.ihanoi_backend.util.JwtTokenUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final WardRepository wardRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthorizationService authorizationService;
    private final FileService fileService;

    public UserService(UserRepository userRepository, WardRepository wardRepository,
                       PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil,
                       AuthorizationService authorizationService, FileService fileService) {
        this.userRepository = userRepository;
        this.wardRepository = wardRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authorizationService = authorizationService;
        this.fileService = fileService;
    }

    public RegisterUserResponse register(CreateUserDto createUserDto) throws BizException {
        User existingUser = userRepository.findByPhoneNumber(createUserDto.getPhoneNumber());
        if (existingUser != null) throw new BizException("Số điện thoại này đã được đăng kí");
        if (!wardRepository.existsById(createUserDto.getWardCode())) throw new BizException("Phường không tồn tại");
        User user = new User();
        String encodePassword = passwordEncoder.encode(createUserDto.getPassword());
        user.setPassword(encodePassword);
        user.setUsername(createUserDto.getPhoneNumber());
        user.setPhoneNumber(createUserDto.getPhoneNumber());
        user.setRole(Const.User.Role.STANDARD);
        user.setAddress(createUserDto.getAddress());
        user.setWardCode(createUserDto.getWardCode());
        user.setFullname(createUserDto.getFullname());
        user = userRepository.save(user);

        return RegisterUserResponse.builder().id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .address(user.getAddress())
                .wardCode(user.getWardCode())
                .fullname(user.getFullname())
                .role(user.getRole().name())
                .build();
    }

    public String login(String phoneNumber, String password) throws BizException {
        UserDetails userDetails = findByUsername(phoneNumber);
        if (userDetails == null) throw new BizException("Tài khoản không tồn tại");
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BizException("Mật khẩu không đúng");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(userDetails);
    }


    public String refreshToken(String token) throws BizException {
        return jwtTokenUtil.refreshHeadToken(token);
    }

    public AdminUserDetails findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        return new AdminUserDetails(user);
    }

    public User findByCitizenId(String citizenId) {
        return userRepository.findByCitizenNumber(citizenId);
    }

    public User save(User user) throws BizException {
         return userRepository.save(user);
    }

    public RegisterUserResponse getCurrentUser() {
        AdminUserDetails adminUser = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = adminUser.getUser();

        return RegisterUserResponse.builder().id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .address(user.getAddress())
                .wardCode(user.getWardCode())
                .fullname(user.getFullname())
                .role(user.getRole().name())
                .build();
    }

    public UserDto promoteUser(Long userId) throws BizException {
        if (!this.authorizationService.isRoot()) {
            throw new BizException("Từ chối truy cập");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new BizException("Không tìm thấy người dùng với id: " + userId)
        );

        user.setRole(Const.User.Role.OFFICER);
        this.userRepository.save(user);

        return UserMapper.INSTANCE.entityToDto(user);
    }

    public UserDto demoteUser(Long userId) throws BizException {
        if (!this.authorizationService.isRoot()) {
            throw new BizException("Từ chối truy cập");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new BizException("Không tìm thấy người dùng với id: " + userId)
        );

        user.setRole(Const.User.Role.STANDARD);
        this.userRepository.save(user);

        return UserMapper.INSTANCE.entityToDto(user);
    }


    public Page<UserDto> search(SearchUserCriteria criteria, Pageable pageable) {

        UserSpecification userSpecification = new UserSpecification(criteria);

        return this.userRepository.findAll(userSpecification, pageable).map(
                UserMapper.INSTANCE::entityToDto
        );
    }

    public UserDto changePassword(UpdatingPasswordDto updatingPasswordDto) throws BizException {

        User user = this.authorizationService.getCurrentUser();

        if (!this.passwordEncoder.matches(updatingPasswordDto.getOldPassword(), user.getPassword())) {
            throw new BizException("Mật khẩu cũng không đúng");
        }

        if (updatingPasswordDto.getOldPassword().equals(updatingPasswordDto.getNewPassword())) {
            throw new BizException("Mật khẩu mới trùng với mật khẩu cũ");
        }

        user.setPassword(passwordEncoder.encode(updatingPasswordDto.getNewPassword()));

        this.userRepository.save(user);

        return UserMapper.INSTANCE.entityToDto(user);
    }

    public UserDto updateInfo(UpdateUserInfoDto updateUserInfoDto) throws BizException {
        if (!authorizationService.getCurrentUser().getId().equals(updateUserInfoDto.getId()))
            throw new BizException("Chỉ user mới tự sửa được thông tin");
        User user = userRepository.findById(updateUserInfoDto.getId()).orElse(null);
        if (user == null)
            throw new BizException("Người dùng không tồn tại");

        if (updateUserInfoDto.getFullname() != null) user.setFullname(updateUserInfoDto.getFullname());
        if (updateUserInfoDto.getAddress() != null) user.setAddress(updateUserInfoDto.getAddress());
        if (updateUserInfoDto.getGender() != null) user.setGender(updateUserInfoDto.getGender());
        if (updateUserInfoDto.getCitizenNumber() != null) user.setCitizenNumber(updateUserInfoDto.getCitizenNumber());
        if (updateUserInfoDto.getEmail() != null) user.setEmail(updateUserInfoDto.getEmail());
        if (updateUserInfoDto.getBirthday() != null) user.setBirthday(updateUserInfoDto.getBirthday());
        if (updateUserInfoDto.getAvatar() != null) {
            Optional<File> fileOptional = fileService.findById(updateUserInfoDto.getAvatar().getId());
            if (fileOptional.isEmpty())
                throw new BizException("File " + updateUserInfoDto.getAvatar().getFileName() + " không tồn tại");
            String mimeType = fileOptional.get().getMimeType();
            if (!mimeType.startsWith("image/") && !mimeType.startsWith("video/"))
                throw new BizException("File " + fileOptional.get().getFileName() + " không phải là ảnh hoặc video");
            user.setAvatar(updateUserInfoDto.getAvatar());
        }

        return UserMapper.INSTANCE.entityToDto(userRepository.save(user));
    }

    public UserDto getUserInfo(Long userId) throws BizException {
        User user = userRepository.findById(userId).orElseThrow(() -> new BizException("Không tồn tại người dùng này"));
        return UserMapper.INSTANCE.entityToDto(user);
    }
}
