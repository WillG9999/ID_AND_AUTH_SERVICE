package com.SocialX.ID_AUTH_SERVICE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection if you're building a stateless API
                .csrf(csrf -> csrf.disable())

                // Allow access to the H2 console and the registration endpoint without authentication
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/h2-console/**").permitAll()  // Permit all access to H2 console
                        .requestMatchers("/api/auth/register").permitAll() // Permit access to registration endpoint
                        .anyRequest().authenticated()
                )

                // Disable frame options to allow the H2 console to display its UI
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))

                // Enable HTTP Basic authentication for other endpoints if necessary
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
