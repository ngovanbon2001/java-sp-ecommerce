package ecomerce.service;

import ecomerce.bo.AdminUserDetails;
import ecomerce.entity.User;
import ecomerce.exception.BizException;
import ecomerce.repository.UserRepository;
import ecomerce.util.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String login(String email, String password) throws BizException {
        UserDetails userDetails = findByEmail(email);
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

    public AdminUserDetails findByEmail(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) return null;
        return new AdminUserDetails(user);
    }

    public User findByCitizenId(String citizenId) {
        return userRepository.findByCitizenNumber(citizenId);
    }

    public User save(User user) throws BizException {
         return userRepository.save(user);
    }
}
