package com.SocialX.ID_AUTH_SERVICE;

import com.SocialX.ID_AUTH_SERVICE.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Register the JWT authentication filter as a bean
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for stateless APIs
                .csrf(csrf -> csrf.disable())

                // Set session management to stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configure URL-based authorization
                .authorizeHttpRequests(authz -> authz
                        // Allow unauthenticated access to login and registration endpoints
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )

                // Add the JWT filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                // Use HTTP Basic for any fallback (optional)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
