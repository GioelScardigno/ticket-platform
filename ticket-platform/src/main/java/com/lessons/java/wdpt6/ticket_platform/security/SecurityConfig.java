package com.lessons.java.wdpt6.ticket_platform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/tickets/create", "/tickets/*/delete").hasAuthority("ADMIN")
                .requestMatchers("/users/**").hasAuthority("ADMIN")
                .requestMatchers("/categories", "/categories/create", "/categories/*/edit", "/categories/*/delete").hasAuthority("ADMIN")
                .requestMatchers("/**").hasAnyAuthority("OPERATOR", "ADMIN")
                .requestMatchers("/api**").permitAll())
                .formLogin(Customizer.withDefaults())
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;

    }

    @Bean
    DatabaseUserDetailsService userDetailsService() {

        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
