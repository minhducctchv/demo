package com.example.demo.config;

import com.example.demo.constant.Role;
import com.example.demo.service.system_bean.CustomAuthenticationEntryPoint;
import com.example.demo.service.system_bean.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public CustomUserDetailService customUserDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                // sử dụng ant matcher | regex matcher | method matcher
                // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(antMatcher("/admin/**")).hasAnyAuthority(Role.ADMIN.toString(), Role.DEVELOPER.toString())
                        .requestMatchers(antMatcher("/user/**")).hasAnyAuthority(Role.USER.toString(), Role.DEVELOPER.toString())
                        .requestMatchers(antMatcher("/logout")).permitAll()
                        .requestMatchers(antMatcher("/login")).permitAll()
                        .requestMatchers(antMatcher("/public/**")).permitAll() // sử dụng antMatcher hoặc mvc.pattern để spring biết la ant hay mvc
                        .requestMatchers(antMatcher("/test/**")).permitAll()
                        .anyRequest().authenticated()
                );

//        http.exceptionHandling((exception)-> exception.authenticationEntryPoint(customAuthenticationEntryPoint));

        http.cors(withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
