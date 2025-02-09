package com.SocialX.ID_AUTH_SERVICE;
// This package declaration should match your directory structure

// Import JPA annotations from the Jakarta Persistence API
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * The @Entity annotation tells Hibernate (and JPA) that this class is a persistent entity.
 */
@Entity

/**
 * The @Table annotation allows you to specify the table name in the database.
 * Here, we use "users" (instead of "user") to avoid conflict with the reserved keyword "user".
 */
@Table(name = "users")
public class User {

    /**
     * The @Id annotation marks this field as the primary key.
     * @GeneratedValue with strategy = GenerationType.IDENTITY specifies that the database will automatically generate the primary key value.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The username field to store the user's name.
    private String username;

    // The password field to store the user's (encoded) password.
    private String password;

    // The roles field to store a comma-separated list of roles (e.g., "ROLE_USER,ROLE_ADMIN").
    private String roles;

    // Default constructor required by JPA.
    public User() {}

    // Getter and setter for id.
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Getter and setter for username.
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and setter for password.
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and setter for roles.
    public String getRoles() {
        return roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }
}

/*
Comprehensive Explanation:

1. Package Declaration:
   - 'package com.SocialX.ID_AUTH_SERVICE.model;' organizes this class in the proper namespace.

2. Imports:
   - We import the necessary JPA annotations (Entity, Table, Id, GeneratedValue, GenerationType) from the Jakarta Persistence API.

3. @Entity Annotation:
   - Marks the class as a JPA entity. Hibernate will map this class to a database table.

4. @Table Annotation:
   - Specifies the name of the table in the database. Here we use @Table(name = "users") instead of the default "user".
   - This change avoids conflicts with reserved SQL keywords (like "user" in H2).

5. Primary Key Configuration:
   - The id field is marked with @Id to indicate it is the primary key.
   - @GeneratedValue(strategy = GenerationType.IDENTITY) tells Hibernate to let the database generate the ID (using auto-increment).

6. Field Definitions:
   - The class defines three additional fields: username, password, and roles.
   - These will correspond to columns in the "users" table.

7. Constructors, Getters, and Setters:
   - A default constructor is provided (required by JPA).
   - Standard getters and setters are defined to allow Hibernate and your application to read and modify the entity's properties.

*/