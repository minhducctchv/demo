package com.example.demo.config;

import com.example.demo.constant.Role;
import com.example.demo.service.system_bean.CustomAuthenticationEntryPoint;
import com.example.demo.service.system_bean.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.toString())
                        .requestMatchers("/user/**").hasAuthority(Role.USER.toString())
                        .requestMatchers(("/login")).permitAll()
                        .requestMatchers(("/logout")).permitAll()
                        .requestMatchers(("/public/**")).permitAll()
                        .anyRequest().authenticated()
                );

        http.exceptionHandling((exception)-> exception.authenticationEntryPoint(customAuthenticationEntryPoint));

        return http.build();
    }

}
