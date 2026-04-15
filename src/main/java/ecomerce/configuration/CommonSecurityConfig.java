package ecomerce.configuration;

import ecomerce.configuration.security.JwtAuthenticationTokenFilter;
import ecomerce.configuration.security.RestAuthenticationEntryPoint;
import ecomerce.filter.AppleRedirectFilter;
import ecomerce.filter.LoggingFilter;
import ecomerce.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurity通用配置
 * 包括通用Bean、Security通用Bean及动态权限通用Bean
 * Created by macro on 2022/5/20.
 */
@Configuration
public class CommonSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }

    @Bean
    AppleRedirectFilter appleRedirectFilter() {
        return new AppleRedirectFilter();
    }

//    @Bean
//    public IgnoreUrlsConfig ignoreUrlsConfig() {
//        return new IgnoreUrlsConfig();
//    }
//
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }
//
//    @Bean
//    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
//        return new RestfulAccessDeniedHandler();
//    }
//
//    @Bean
//    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
//        return new RestAuthenticationEntryPoint();
//    }
//

//
//    @ConditionalOnBean(name = "dynamicSecurityService")
//    @Bean
//    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
//        return new DynamicAccessDecisionManager();
//    }
//
//    @ConditionalOnBean(name = "dynamicSecurityService")
//    @Bean
//    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
//        return new DynamicSecurityMetadataSource();
//    }
//
//    @ConditionalOnBean(name = "dynamicSecurityService")
//    @Bean
//    public DynamicSecurityFilter dynamicSecurityFilter(){
//        return new DynamicSecurityFilter();
//    }
}
