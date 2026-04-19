package ecomerce.configuration;

import ecomerce.common.Const;
import ecomerce.configuration.security.JwtAuthenticationTokenFilter;
import ecomerce.filter.AppleRedirectFilter;
import ecomerce.filter.LoggingFilter;
import ecomerce.security.OAuth2AuthenticationSuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private LoggingFilter loggingFilter;

    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private AppleRedirectFilter appleRedirectFilter;

    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;



    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults()).csrf(AbstractHttpConfigurer::disable);
        // OAuth2 state uses session while APIs remain JWT protected.
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(Const.AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated());
        // OAuth2 disabled for development - comment out if not needed
        // http.oauth2Login(oauth2 -> oauth2.successHandler(oAuth2AuthenticationSuccessHandler));
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(loggingFilter, JwtAuthenticationTokenFilter.class);
        http.addFilterBefore(appleRedirectFilter, LoggingFilter.class);
        return http.build();
    }


    @Bean
    WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }

        };
    }

}