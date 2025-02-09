package com.SocialX.ID_AUTH_SERVICE;

import com.SocialX.ID_AUTH_SERVICE.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; // Used to authenticate the user's credentials

    @Autowired
    private JwtUtil jwtUtil;

    // Registration endpoint (example provided earlier)
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        // Registration logic here (save user, encode password, etc.)
        return "User registered successfully";
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
