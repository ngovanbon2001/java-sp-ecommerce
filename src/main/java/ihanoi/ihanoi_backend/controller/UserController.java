package ihanoi.ihanoi_backend.controller;

import ihanoi.ihanoi_backend.bo.AdminUserDetails;
import ihanoi.ihanoi_backend.common.BasePagingResponse;
import ihanoi.ihanoi_backend.common.BaseResponse;
import ihanoi.ihanoi_backend.entity.User;
import ihanoi.ihanoi_backend.exception.BizException;
import ihanoi.ihanoi_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "user")
public class UserController {
    // private final VneidService vneidService;
    // @Value("${jwt.tokenHeader}")
    // private String tokenHeader;

    // private final UserService userService;

    // public UserController(UserService userService, VneidService vneidService) {
    //     this.userService = userService;
    //     this.vneidService = vneidService;
    // }

    // @PostMapping("register")
    // @Operation(summary = "Đăng kí", description = "Dành cho người dùng thường", tags = {"user"})
    // public BaseResponse<RegisterUserResponse> register(@Valid @RequestBody CreateUserDto createUserDto) throws BizException {
    //     return BaseResponse.success(userService.register(createUserDto));
    // }

    // @PostMapping("login")
    // @Operation(summary = "Đăng nhập", description = "Dành cho người dùng thường", tags = {"user"})
    // public BaseResponse<JwtTokenResponse> login(@Valid @RequestBody UserLoginDto userLoginDto) throws BizException {
    //     String token = userService.login(userLoginDto.getPhoneNumber(), userLoginDto.getPassword());
    //     return BaseResponse.success(new JwtTokenResponse(token));
    // }

    // @GetMapping("user-profile")
    // @Operation(summary = "Thông tin cá nhân", description = "Dành cho người dùng thường", tags = {"user"})
    // public BaseResponse<UserDto> userProfile() throws BizException {
    //     return BaseResponse.success(userService.getUserInfo(userService.getCurrentUser().getId()));
    // }

    // @PostMapping("refresh-token")
    // @Operation(summary = "Refresh token", description = "Dành cho người dùng thường", tags = {"user"})
    // public BaseResponse<JwtTokenResponse> refreshToken(HttpServletRequest request) throws BizException {
    //     String token = request.getHeader(tokenHeader);
    //     return BaseResponse.success(new JwtTokenResponse(userService.refreshToken(token)));
    // }

    // @PatchMapping("{userId}/staff")
    // @Operation(summary = "Nâng cấp thành cán bộ", tags = {"user"})
    // public BaseResponse<UserDto> promoteUser(@Valid @PathVariable() Long userId) throws BizException {

    //     return BaseResponse.success(this.userService.promoteUser(userId));
    // }

    // @PatchMapping("{userId}/staff/demote")
    // @Operation(summary = "Chuyển từ cán bộ thành người dùng thông thường", tags = {"user"})
    // public BaseResponse<UserDto> demoteStaff(@Valid @PathVariable() Long userId) throws BizException {

    //     return BaseResponse.success(this.userService.demoteUser(userId));
    // }

    // @GetMapping("search")
    // @PreAuthorize("hasAnyAuthority('OFFICER', 'ROOT')")
    // @Operation(summary = "Tìm kiếm người dùng", description = "Dành cho cán bộ", tags = {"user"})
    // public BasePagingResponse<UserDto> search(
    //         @RequestParam(name = "roles", required = false) List<String> roles,
    //         @RequestParam(name = "username", required = false) String userName,
    //         @RequestParam(name = "phone_number", required = false) String phoneNumber,
    //         @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    // ) {
    //     SearchUserCriteria criteria = new SearchUserCriteria(roles, userName, phoneNumber);

    //     return new BasePagingResponse<>(this.userService.search(criteria, pageable));
    // }

    // @PatchMapping("password")
    // @Operation(summary = "Đổi mật khẩu", tags = {"user"})
    // public BaseResponse<UserDto> changePassword(@Valid @RequestBody UpdatingPasswordDto updatingPasswordDto) throws BizException {

    //     return BaseResponse.success(this.userService.changePassword(updatingPasswordDto));
    // }

    // @PatchMapping("info")
    // @Operation(summary = "Cập nhật thông tin cá nhân", tags = {"user"})
    // public BaseResponse<UserDto> updateInfo(@Valid @RequestBody UpdateUserInfoDto updateUserInfoDto) throws BizException {

    //     return BaseResponse.success(this.userService.updateInfo(updateUserInfoDto));
    // }


    // @GetMapping("{id}/profile")
    // @PreAuthorize("hasAnyAuthority('OFFICER', 'ROOT')")
    // @Operation(summary = "Thông tin cá nhân", description = "Dành cho cán bộ", tags = {"user"})
    // public BaseResponse<UserDto> userProfile(@PathVariable(name = "id") Long userId) throws BizException {
    //     return BaseResponse.success(userService.getUserInfo(userId));
    // }

    // @GetMapping("sso-token")
    // @Operation(summary = "Get sso token", tags = {"user"})
    // public BaseResponse<SsoToken> getSsoToken(HttpServletRequest request) throws BizException {
    //     Map<String, String> map = new LinkedHashMap<>();
    //     String rawQuery = request.getQueryString();

    //     String[] pairs = rawQuery.split("&");
    //     for (String pair : pairs) {
    //         String[] kv = pair.split("=", 2);
    //         String rawKey = kv[0];
    //         String rawVal = kv.length > 1 ? kv[1] : "";
    //         String key = URLDecoder.decode(rawKey, StandardCharsets.UTF_8);
    //         String normalizedValue = rawVal.replace("+", "%2B");
    //         String value = URLDecoder.decode(normalizedValue, StandardCharsets.UTF_8);
    //         map.put(key, value);
    //     }

    //     String code = map.get("code");
    //     if (code == null) {
    //         throw new BizException("Thiếu param code");
    //     }
    //     String encryptKey = map.get("encrypt_key");
    //     if (encryptKey == null) {
    //         throw new BizException("Thiếu param encrypt_key");
    //     }
    //     return BaseResponse.success(vneidService.getSsoToken(code, encryptKey));
    // }

    // @GetMapping("check-sso-user")
    // @Operation(summary = "Check để cập nhật số đt", tags = {"user"})
    // public BaseResponse<NeedUpdatePhoneNumber> checkSsoToken() {
    //     AdminUserDetails adminUser = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     User user = adminUser.getUser();
    //     return BaseResponse.success(new NeedUpdatePhoneNumber(Objects.equals(user.getVerified(), (short) 0)));
    // }

    // @PostMapping("sso/update-phone-number")
    // @Operation(summary = "cập nhật số đt cho tài khoản từ vneid", tags = {"user"})
    // public BaseResponse<UserDto> updateSsoPhoneNumber(@Valid @RequestBody UpdateSsoPhoneNumber ssoPhoneNumber) throws BizException {
    //     AdminUserDetails adminUser = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     User user = adminUser.getUser();
    //     user.setVerified((short) 1);
    //     user.setPhoneNumber(ssoPhoneNumber.getPhoneNumber());
    //     return BaseResponse.success(UserMapper.INSTANCE.entityToDto(userService.save(user)));
    // }


}
