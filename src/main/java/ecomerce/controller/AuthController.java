package ecomerce.controller;

import ecomerce.common.BaseResponse;
import ecomerce.dto.auth.AuthTokenResponse;
import ecomerce.dto.auth.ForgotPasswordRequest;
import ecomerce.dto.auth.LoginRequest;
import ecomerce.dto.auth.RefreshTokenRequest;
import ecomerce.dto.auth.RegisterRequest;
import ecomerce.dto.auth.ResetPasswordRequest;
import ecomerce.exception.BizException;
import ecomerce.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Authentication API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public BaseResponse<String> register(@Valid @RequestBody RegisterRequest request) throws BizException {
        authService.register(request);
        return BaseResponse.success("Registered");
    }

    @PostMapping("/login")
    public BaseResponse<AuthTokenResponse> login(@Valid @RequestBody LoginRequest request) throws BizException {
        return BaseResponse.success(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public BaseResponse<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) throws BizException {
        authService.forgotPassword(request);
        return BaseResponse.success("OTP sent to email");
    }

    @PostMapping("/reset-password")
    public BaseResponse<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) throws BizException {
        authService.resetPassword(request);
        return BaseResponse.success("Password updated");
    }

    @PostMapping("/refresh-token")
    public BaseResponse<AuthTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) throws BizException {
        return BaseResponse.success(authService.refreshToken(request));
    }
}
