package com.SocialX.ID_AUTH_SERVICE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsServiceConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // Creating a default in-memory user for testing
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("testUser")
                        .password("{noop}testPassword") // {noop} means no password encoder is applied
                        .roles("USER")
                        .build()
        );
        return manager;
    }
}
