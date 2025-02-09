package com.SocialX.ID_AUTH_SERVICE;
// Declares the package for this repository interface

// Import the JpaRepository interface from Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository;

// Import Optional from the Java standard library
import java.util.Optional;

// Import the User entity. Make sure the User class is in the correct package.
import com.SocialX.ID_AUTH_SERVICE.User;

// Declare the repository interface by extending JpaRepository.
// The first parameter is the entity type (User) and the second is the type of the entity's primary key (Long).
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom method to find a user by their username.
    // Spring Data JPA will automatically implement this method based on the method name.
    Optional<User> findByUsername(String username);
}

/*
Comprehensive Explanation:

1. Package Declaration:
   - 'package com.SocialX.ID_AUTH_SERVICE;' organizes this interface within your project's namespace.

2. Imports:
   - 'import org.springframework.data.jpa.repository.JpaRepository;' brings in the repository functionality from Spring Data JPA.
   - 'import java.util.Optional;' imports the Optional class from Java's standard library, which is used to represent an optional value.
   - 'import com.SocialX.ID_AUTH_SERVICE.User;' ensures that the User entity is available for use in this repository.

3. Interface Declaration:
   - The interface extends JpaRepository<User, Long>, which provides a range of built-in CRUD operations for the User entity.

4. Custom Method Declaration:
   - 'Optional<User> findByUsername(String username);'
     * This method uses Spring Data JPA’s naming conventions to automatically generate the required query.
     * It returns an Optional<User>, which will contain the User if found, or be empty if no user exists with the provided username.
*/
