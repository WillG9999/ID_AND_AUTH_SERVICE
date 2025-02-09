package com.SocialX.ID_AUTH_SERVICE;

import com.SocialX.ID_AUTH_SERVICE.User;
import com.SocialX.ID_AUTH_SERVICE.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController  // Marks this class as a REST controller
@RequestMapping("/api/auth")  // Base URL for all authentication-related endpoints
public class AuthController {

    @Autowired  // Injects the UserRepository dependency
    private UserRepository userRepository;

    @Autowired  // Injects the PasswordEncoder bean for encoding passwords
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")  // Maps POST requests to /api/auth/register to this method
    public String register(@RequestBody User user) {
        // Check if the username already exists
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            return "Error: Username is already taken!";
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Set a default role for the user (e.g., ROLE_USER)
        user.setRoles("ROLE_USER");
        // Save the new user to the database
        userRepository.save(user);
        return "User registered successfully";
    }
}

/*
Explanation:
- This REST controller provides an endpoint for user registration.
- The @PostMapping annotation maps HTTP POST requests to the register method.
- The method uses the PasswordEncoder to encode the user's password and then saves the user.
*/
