package com.example.AuthSer.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .formLogin(form -> form.disable()) // ⛔ تعطيل login form نهائياً
        .httpBasic(httpBasic -> httpBasic.disable()) // ⛔ تعطيل basic auth كمان لو مش محتاجه
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()

            .anyRequest().authenticated()
        );

    return http.build();
}


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

