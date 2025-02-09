package com.SocialX.ID_AUTH_SERVICE;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration  // Indicates that this class provides Spring configuration
public class AppConfig {

    @Bean  // Registers the PasswordEncoder bean in the Spring context
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Uses BCrypt to securely hash passwords
    }
}

/*
Explanation:
- This configuration class defines a bean for PasswordEncoder.
- The BCryptPasswordEncoder is a robust hashing algorithm commonly used to store passwords securely.
*/
