package com.SocialX.ID_AUTH_SERVICE;
// Declares the package in which this class resides

// Import Spring's annotation for dependency injection
import org.springframework.beans.factory.annotation.Autowired;
// Import the SimpleGrantedAuthority class used to represent a user's role/authority
import org.springframework.security.core.authority.SimpleGrantedAuthority;
// Import the UserDetails interface representing the authenticated user in Spring Security
import org.springframework.security.core.userdetails.UserDetails;
// Import the UserDetailsService interface which is used by Spring Security to load user data
import org.springframework.security.core.userdetails.UserDetailsService;
// Import the exception that is thrown when a user cannot be found by their username
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// Import the @Service annotation to mark this class as a Spring service component
import org.springframework.stereotype.Service;

// Import utilities for array manipulation and stream processing
import java.util.Arrays;
import java.util.List;
// Import the Collectors utility to convert a stream to a list
import java.util.stream.Collectors;

// @Service tells Spring that this class is a service component (it can be injected where needed)
@Service
// The class implements UserDetailsService to provide user details required by Spring Security
public class CustomUserDetailsService implements UserDetailsService {

    // Inject the UserRepository to interact with the user data in the database
    @Autowired
    private UserRepository userRepository;

    /**
     * Loads the user by their username.
     * This method is used by Spring Security during authentication.
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated UserDetails instance (a Spring Security user).
     * @throws UsernameNotFoundException if the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the user from the repository by username.
        // The findByUsername method returns an Optional<User>.
        User user = userRepository.findByUsername(username)
                // If the Optional is empty (i.e., no user is found), throw a UsernameNotFoundException.
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Convert the roles (stored as a comma-separated string, e.g., "ROLE_USER,ROLE_ADMIN")
        // into a list of SimpleGrantedAuthority objects, which Spring Security uses to manage authorities.
        List<SimpleGrantedAuthority> authorities = Arrays.stream(user.getRoles().split(","))
                // For each role in the array, create a new SimpleGrantedAuthority object.
                .map(SimpleGrantedAuthority::new)
                // Collect the results of the mapping into a List.
                .collect(Collectors.toList());

        // Create and return a Spring Security User object that implements UserDetails.
        // This object is constructed with the username, the (encoded) password, and the list of authorities.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), // The username from the database
                user.getPassword(), // The encoded password from the database
                authorities         // The list of roles/authorities for the user
        );
    }
}

/*
Comprehensive Explanation:

1. Package Declaration:
   - 'package com.socialx.id_auth_service.service;' organizes the class within the project's namespace.

2. Imports:
   - Import statements bring in necessary classes such as the User entity, UserRepository for data access,
     Spring annotations for dependency injection and component scanning, and utility classes for data manipulation.

3. Class Declaration and Annotations:
   - The class is annotated with @Service, marking it as a service component that Spring will manage.
   - The class implements the UserDetailsService interface, which requires implementing the loadUserByUsername method.

4. Dependency Injection:
   - The UserRepository is injected via the @Autowired annotation, allowing access to user data from the database.

5. loadUserByUsername Method:
   - This method attempts to retrieve a user by their username using the userRepository.
   - It uses Optional.orElseThrow to throw a UsernameNotFoundException if no user is found.
   - The user's roles (stored as a comma-separated string) are converted into a list of SimpleGrantedAuthority objects.
   - A new Spring Security User (implementing UserDetails) is created and returned, containing the username,
     the encoded password, and the list of authorities.

6. Each line of code includes comments explaining its purpose and how it fits into the overall authentication mechanism.
*/
