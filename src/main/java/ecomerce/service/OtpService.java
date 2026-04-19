package ecomerce.service;

import ecomerce.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OtpService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final StringRedisTemplate redisTemplate;
    private final JavaMailSender javaMailSender;

    @Value("${auth.otp.ttl-minutes:5}")
    private long otpTtlMinutes;

    @Value("${app.mail.from:no-reply@ecommerce.local}")
    private String fromEmail;

    public void sendForgotPasswordOtp(String email) throws BizException {
        String otp = generateOtp();
        redisTemplate.opsForValue().set(buildOtpKey(email), otp, Duration.ofMinutes(otpTtlMinutes));
        sendOtpEmail(email, otp);
    }

    public void verifyOtpAndConsume(String email, String otp) throws BizException {
        String key = buildOtpKey(email);
        String expected = redisTemplate.opsForValue().get(key);
        if (expected == null) {
            throw new BizException("OTP is expired or not found");
        }
        if (otp == null || !expected.equals(otp.trim())) {
            throw new BizException("OTP is invalid");
        }
        redisTemplate.delete(key);
    }

    private String buildOtpKey(String email) {
        return "otp:" + email;
    }

    private String generateOtp() {
        int value = SECURE_RANDOM.nextInt(900_000) + 100_000;
        return Integer.toString(value);
    }

    private void sendOtpEmail(String email, String otp) throws BizException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Password reset OTP");
        message.setText("Your OTP is " + otp + ". It expires in " + otpTtlMinutes + " minutes.");

        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new BizException("Cannot send OTP email", ex);
        }
    }
}
