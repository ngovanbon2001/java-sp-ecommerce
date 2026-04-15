package ihanoi.ihanoi_backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    private String generateToken(Map<String, Object> claims) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withPayload(claims)
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
    }


    private Map<String, Claim> getClaimsFromToken(String token) {
        Map<String, Claim> claims = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            claims = decodedJWT.getClaims();
        } catch (Exception e) {
            log.info("JWT decode error :{}", e.getMessage());
        }
        return claims;
    }


    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }


    public String getUserNameFromToken(String token) {
        String username;
        try {
            Map<String, Claim> claims = getClaimsFromToken(token);
            username = claims.get(CLAIM_KEY_USERNAME).asString();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getExpiresAt().before(new Date());

        } catch (Exception e) {
            log.info("JWT decode error :{}", e.getMessage());
        }
        return false;
    }


    private Date getExpiredDateFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getExpiresAt();

        } catch (Exception e) {
            log.info("JWT decode error :{}", e.getMessage());
        }
        return null;
    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }


    public String refreshHeadToken(String oldToken) {
        if (StringUtils.isEmpty(oldToken)) {
            return null;
        }
        String token = oldToken.substring(tokenHead.length());
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        //Token verification failed
        Map<String, Claim> claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        //If the token has expired, refresh is not supported
        if (isTokenExpired(token)) {
            return null;
        }
        //If the token has just been refreshed within 30 minutes, return the original token
        if (tokenRefreshJustBefore(token, 30 * 60)) {
            return token;
        } else {
            Map<String, Object> newClaims = claims.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            newClaims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(newClaims);
        }
    }


    private boolean tokenRefreshJustBefore(String token, int time) {
        Map<String, Claim> claims = getClaimsFromToken(token);
        ZonedDateTime created = DateTimeUtils.fromDate(claims.get(CLAIM_KEY_CREATED).asDate());
        ZonedDateTime refreshDate = DateTimeUtils.now();
        return refreshDate.isAfter(created) && refreshDate.isBefore(created.plusSeconds(time));
    }
}
