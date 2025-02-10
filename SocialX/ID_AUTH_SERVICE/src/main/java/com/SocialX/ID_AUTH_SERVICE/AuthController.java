package com.SocialX.ID_AUTH_SERVICE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager; // Used to authenticate the user's credentials

    @Autowired
    private JwtUtil jwtUtil;

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            logger.debug("Received registration request");
            logger.debug("Request details - username: {}, email: {}", 
                request.getUsername(), request.getEmail());

            // Validate request
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }

            User user = userService.registerUser(request);
            logger.info("User registered successfully: {}", user.getUsername());
            
            return ResponseEntity.ok()
                    .body(Map.of(
                            "message", "User registered successfully",
                            "username", user.getUsername(),
                            "email", user.getEmail(),
                            "id", user.getId()
                    ));
        } catch (Exception e) {
            logger.error("Registration failed with error: ", e);
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Authenticate the user using the AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Set the authentication in the SecurityContext (optional, for further use)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a JWT token using the username from the authentication principal
        String jwt = jwtUtil.generateToken(authentication.getName());

        // Return the token in the response
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}


/*
Explanation:
- This REST controller provides an endpoint for user registration.
- The @PostMapping annotation maps HTTP POST requests to the register method.
- The method uses the PasswordEncoder to encode the user's password and then saves the user.
*/
