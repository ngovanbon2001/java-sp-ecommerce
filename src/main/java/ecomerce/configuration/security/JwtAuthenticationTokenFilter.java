package ecomerce.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecomerce.common.Const;
import ecomerce.security.JwtTokenProvider;
import ecomerce.service.AuthService;
import ecomerce.util.RequestMatchers;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final List<RequestMatcher> WHITE_LIST_MATCHERS = RequestMatchers.antMatchers(Const.AUTH_WHITELIST);

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RequestScope requestScope;

    @Value("${jwt.tokenHeader:Authorization}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        if (RequestMatchers.chainRequestMatchers(request, WHITE_LIST_MATCHERS)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(tokenHeader);
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(BEARER_PREFIX)) {
            failedAuthentication(response, "No access token provided", HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String accessToken = authHeader.substring(BEARER_PREFIX.length()).trim();
        if (!jwtTokenProvider.validateAccessToken(accessToken)) {
            failedAuthentication(response, "Access token is invalid or expired", HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String email = jwtTokenProvider.getSubject(accessToken);
        if (email == null) {
            failedAuthentication(response, "Access token is invalid", HttpStatus.UNAUTHORIZED.value());
            return;
        }

        UserDetails userDetails = authService.loadUserByEmail(email);
        if (userDetails == null) {
            failedAuthentication(response, "User not found", HttpStatus.UNAUTHORIZED.value());
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        requestScope.setUsername(email);
        requestScope.setSub(email);
        requestScope.setUsrc("jwt");

        chain.doFilter(request, response);
    }

    private void failedAuthentication(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Map<String, String> error = new HashMap<>();
        error.put("error_message", message);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
