package ecomerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ecomerce.entity.Customer;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String CLAIM_TOKEN_TYPE = "token_type";
    private static final String ACCESS_TOKEN_TYPE = "access";
    private static final String REFRESH_TOKEN_TYPE = "refresh";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${auth.jwt.access-token-expiration-minutes:15}")
    private long accessTokenExpirationMinutes;

    @Value("${auth.jwt.refresh-token-expiration-days:7}")
    private long refreshTokenExpirationDays;

    private SecretKey signingKey;

    @PostConstruct
    void init() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
            signingKey = Keys.hmacShaKeyFor(keyBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Cannot initialize JWT signing key", e);
        }
    }

    public String generateAccessToken(Customer customer) {
        return buildToken(customer, ACCESS_TOKEN_TYPE, Duration.ofMinutes(accessTokenExpirationMinutes));
    }

    public String generateRefreshToken(Customer customer) {
        return buildToken(customer, REFRESH_TOKEN_TYPE, Duration.ofDays(refreshTokenExpirationDays));
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, ACCESS_TOKEN_TYPE);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, REFRESH_TOKEN_TYPE);
    }

    public String getSubject(String token) {
        try {
            return parseClaims(token).getSubject();
        } catch (Exception ex) {
            log.debug("Cannot extract subject from token: {}", ex.getMessage());
            return null;
        }
    }

    public long getAccessTokenExpirationSeconds() {
        return Duration.ofMinutes(accessTokenExpirationMinutes).getSeconds();
    }

    private String buildToken(Customer customer, String tokenType, Duration ttl) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(customer.getEmail())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(ttl)))
                .claim(CLAIM_TOKEN_TYPE, tokenType)
                .claim("role", customer.getRole().name())
                .claim("provider", customer.getProvider().name())
                .signWith(signingKey)
                .compact();
    }

    private boolean validateToken(String token, String expectedType) {
        try {
            Claims claims = parseClaims(token);
            String tokenType = claims.get(CLAIM_TOKEN_TYPE, String.class);
            return expectedType.equals(tokenType) && claims.getExpiration().after(new Date());
        } catch (Exception ex) {
            log.debug("JWT validation failed: {}", ex.getMessage());
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
