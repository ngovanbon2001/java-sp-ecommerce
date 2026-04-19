package ecomerce.service;

import ecomerce.dto.auth.AuthTokenResponse;
import ecomerce.dto.auth.ForgotPasswordRequest;
import ecomerce.dto.auth.LoginRequest;
import ecomerce.dto.auth.RefreshTokenRequest;
import ecomerce.dto.auth.RegisterRequest;
import ecomerce.dto.auth.ResetPasswordRequest;
import ecomerce.entity.Customer;
import ecomerce.entity.CustomerProvider;
import ecomerce.entity.CustomerRole;
import ecomerce.exception.BizException;
import ecomerce.repository.CustomerRepository;
import ecomerce.security.CustomerPrincipal;
import ecomerce.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final OtpService otpService;

    @Transactional
    public void register(RegisterRequest request) throws BizException {
        String email = normalizeEmail(request.getEmail());
        if (customerRepository.existsByEmail(email)) {
            throw new BizException("Email already exists");
        }

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        customer.setRole(CustomerRole.ROLE_USER);
        customer.setProvider(CustomerProvider.LOCAL);

        customerRepository.save(customer);
    }

    public AuthTokenResponse login(LoginRequest request) throws BizException {
        String email = normalizeEmail(request.getEmail());
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new BizException("Invalid email or password"));

        if (customer.getProvider() != CustomerProvider.LOCAL) {
            throw new BizException("This account uses social login");
        }

        if (customer.getPasswordHash() == null || !passwordEncoder.matches(request.getPassword(), customer.getPasswordHash())) {
            throw new BizException("Invalid email or password");
        }

        return issueTokens(customer);
    }

    public void forgotPassword(ForgotPasswordRequest request) throws BizException {
        String email = normalizeEmail(request.getEmail());
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new BizException("Email not found"));
        if (customer.getProvider() != CustomerProvider.LOCAL) {
            throw new BizException("This account uses social login");
        }
        otpService.sendForgotPasswordOtp(email);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) throws BizException {
        String email = normalizeEmail(request.getEmail());
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new BizException("Email not found"));

        if (customer.getProvider() != CustomerProvider.LOCAL) {
            throw new BizException("This account uses social login");
        }

        otpService.verifyOtpAndConsume(email, request.getOtp());

        customer.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        customerRepository.save(customer);
    }

    public AuthTokenResponse refreshToken(RefreshTokenRequest request) throws BizException {
        String refreshToken = request.getRefreshToken();
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new BizException("Refresh token is invalid or expired");
        }

        String email = jwtTokenProvider.getSubject(refreshToken);
        if (email == null) {
            throw new BizException("Refresh token is invalid");
        }

        Customer customer = customerRepository.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new BizException("User not found"));
        return issueTokens(customer);
    }

    @Transactional
    public AuthTokenResponse loginWithGoogle(String email) throws BizException {
        String normalizedEmail = normalizeEmail(email);
        if (normalizedEmail == null || normalizedEmail.isBlank()) {
            throw new BizException("Google account does not contain email");
        }

        Customer customer = customerRepository.findByEmail(normalizedEmail)
                .orElseGet(() -> {
                    Customer created = new Customer();
                    created.setEmail(normalizedEmail);
                    created.setRole(CustomerRole.ROLE_USER);
                    created.setProvider(CustomerProvider.GOOGLE);
                    return customerRepository.save(created);
                });

        if (customer.getProvider() == CustomerProvider.LOCAL) {
            throw new BizException("Email already registered with local account");
        }

        return issueTokens(customer);
    }

    public UserDetails loadUserByEmail(String email) {
        return customerRepository.findByEmail(normalizeEmail(email))
                .map(CustomerPrincipal::new)
                .orElse(null);
    }

    private AuthTokenResponse issueTokens(Customer customer) {
        String accessToken = jwtTokenProvider.generateAccessToken(customer);
        String refreshToken = jwtTokenProvider.generateRefreshToken(customer);
        return new AuthTokenResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtTokenProvider.getAccessTokenExpirationSeconds()
        );
    }

    private String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
