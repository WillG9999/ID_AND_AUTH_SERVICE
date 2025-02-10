package com.SocialX.ID_AUTH_SERVICE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.Calendar;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(RegisterRequest request) {
        try {
            logger.debug("Starting user registration process...");
            
            // Create new user
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setRoles("ROLE_USER");

            // Set security defaults
            user.setEnabled(true);
            user.setEmailVerified(false);
            user.setAccountNonExpired(true);
            user.setFailedAttempt(0);
            user.setLastPasswordChangeDate(new Date());
            user.setPasswordExpiryDate(calculatePasswordExpiryDate());

            // Set SocialX token information
            Date now = new Date();
            user.setSocialXTokenIssued(now);
            user.setSocialXTokenExpiry(new Date(now.getTime() + (60 * 60 * 1000))); // 1 hour expiry
            user.setSocialXAccessToken(generateInitialAccessToken());
            user.setSocialXRefreshToken(generateRefreshToken());

            logger.debug("Attempting to save user to database");
            User savedUser = userRepository.save(user);
            logger.info("Successfully registered user: {}", savedUser.getUsername());
            return savedUser;

        } catch (Exception e) {
            logger.error("Error during user registration: ", e);
            throw new RuntimeException("Error registering user: " + e.getMessage(), e);
        }
    }

    private Date calculatePasswordExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 90); // Password expires in 90 days
        return calendar.getTime();
    }

    private String generateInitialAccessToken() {
        // Generate a random token
        return java.util.UUID.randomUUID().toString();
    }

    private String generateRefreshToken() {
        // Generate a random refresh token
        return java.util.UUID.randomUUID().toString();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
} 