package com.intern.practice.hw10.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authRequests -> authRequests
                        .anyRequest().authenticated()
                )
                .csrf().disable()
                .httpBasic()
                .and()
                .formLogin(form -> form
                        .defaultSuccessUrl("/books/myBooks", true) // true, щоб не було 999 помилки
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout").permitAll()
                )
                .build();
    }
}
