package ecomerce.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecomerce.common.Const;
import ecomerce.service.UserService;
import ecomerce.util.JwtTokenUtil;
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

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private static final List<RequestMatcher> whiteListMatchers = RequestMatchers.antMatchers(Const.AUTH_WHITELIST);


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            if (RequestMatchers.chainRequestMatchers(request, whiteListMatchers)) {
                chain.doFilter(request, response);
                return;
            }
            String authHeader = request.getHeader(this.tokenHeader);
            logger.debug("authHeader:" + authHeader);
            if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
                failedAuthentication(response, "No token provided!", HttpStatus.UNAUTHORIZED.value());
                return;
            }

            String authToken = authHeader
                    .substring("Bearer ".length())
                    .trim();// The part after "Bearer "

            logger.debug("authHeader=[" + authHeader + "]");
            logger.debug("authToken=[" + authToken + "]");
            logger.debug("first char=" + (int) authToken.charAt(0));
            logger.debug("last char=" + (int) authToken.charAt(authToken.length() - 1));

            DecodedJWT jwt = JWT.decode(authToken); // chỉ decode, không verify
            logger.debug("DecodedJWT" + jwt);

            String issuer = jwt.getIssuer();
            logger.debug("issuer" + issuer);
            if (issuer != null) {
//             try {
//                 VneidUserInfo userInfo = vneidService.getVneidUserInfo(authToken);
//                 if (userInfo == null) {
//                     failedAuthentication(response, "Không thể xác thực token", HttpStatus.UNAUTHORIZED.value());
//                     return;
//                 }
//                 User user = userService.findByCitizenId(userInfo.getCitizenPid());
//                 if (user == null) {
//                     user = new User();
//                     user.setCitizenNumber(userInfo.getCitizenPid());
//                     user.setFullname(userInfo.getFullName());
//                     user.setPhoneNumber("");
//                     user.setUsername("");
//                     user.setPassword("");
//                     user.setAddress("");
//                     user.setRole(Const.User.Role.STANDARD);
//                     user.setVerified((short) 0);
//                     user.setWardCode("-1");
//                     user.setBirthday(DateTimeUtils.parseDate(userInfo.getBirthDate(), "dd-MM-yyyy").toLocalDate());
//                 }else {
//                     user.setFullname(userInfo.getFullName());
//                     user.setBirthday(DateTimeUtils.parseDate(userInfo.getBirthDate(), "dd-MM-yyyy").toLocalDate());
//                     user.setCitizenNumber(userInfo.getCitizenPid());
//                 }
//                 userService.save(user);

// //                if(user.getStatus() == 0){
// //                    failedAuthentication(response, "Chưa cập nhật thông tin tài khoản", HttpStatus.UNAUTHORIZED.value());
// //                    return;
// //                }

//                 AdminUserDetails adminUserDetails = new AdminUserDetails(user);
//                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(adminUserDetails, null, adminUserDetails.getAuthorities());
//                 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
// //                log.info("authenticated user:{}", username);
//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//                 chain.doFilter(request, response);

//                 return;
//             } catch (BizException e) {
//                 failedAuthentication(response, "Dịch vụ xác thực trả về lỗi " + e.getMessage(), HttpStatus.UNAUTHORIZED.value());
//                 return;
//             }
            }else {
                String username = jwtTokenUtil.getUserNameFromToken(authToken);
                if (username == null) {
                    failedAuthentication(response, "Token is invalid!", HttpStatus.UNAUTHORIZED.value());
                    return;
                }

//            log.info("checking username:{}", username);
                UserDetails userDetails = this.userService.findByEmail(username);
                if (userDetails == null) {
                    failedAuthentication(response, "User not found!", HttpStatus.UNAUTHORIZED.value());
                    return;
                }

                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                log.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    chain.doFilter(request, response);
                    return;
                }
            }
            failedAuthentication(response, "Token is expired or invalid!", HttpStatus.UNAUTHORIZED.value());
        } catch (Exception e) {
            logger.error("JWT ERROR", e);

            failedAuthentication(response,
                    "Invalid token: " + e.getMessage(),
                    HttpStatus.UNAUTHORIZED.value());
            return;
        }
    }

    private void failedAuthentication(HttpServletResponse response, String message, int statusCode) throws IOException {
//        response.setHeader("error", message);
        logger.info("Not Auth");
        response.setStatus(statusCode);
        Map<String, String> error = new HashMap<>();
        error.put("error_message", message);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

}
