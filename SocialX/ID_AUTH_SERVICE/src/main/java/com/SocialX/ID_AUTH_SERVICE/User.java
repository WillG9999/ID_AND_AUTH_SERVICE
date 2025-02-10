package com.SocialX.ID_AUTH_SERVICE;
// This package declaration should match your directory structure

// Import JPA annotations from the Jakarta Persistence API
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * The @Entity annotation tells Hibernate (and JPA) that this class is a persistent entity.
 */
@Entity

/**
 * The @Table annotation allows you to specify the table name in the database.
 * Here, we use "users" (instead of "user") to avoid conflict with the reserved keyword "user".
 */

@Table(name = "users")
public class User implements UserDetails {

    /**
     * The @Id annotation marks this field as the primary key.
     * @GeneratedValue with strategy = GenerationType.IDENTITY specifies that the database will automatically generate the primary key value.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // SocialX Core User Fields
    @Column(name = "X_username", unique = true, nullable = false)
    private String username;

    @Column(name = "X_password", nullable = false)
    private String password;

    @Column(name = "X_email", unique = true, nullable = false)
    private String email;

    @Column(name = "X_roles")
    private String roles;

    // SocialX Security Fields
    @Column(name = "X_enabled")
    private boolean enabled = true;

    @Column(name = "X_account_non_expired")
    private boolean accountNonExpired = true;
    
    @Column(name = "X_failed_attempt")
    private int failedAttempt;
    
    @Column(name = "X_lock_time")
    private Date lockTime;
    
    @Column(name = "X_last_password_change")
    private Date lastPasswordChangeDate;
    
    @Column(name = "X_password_expiry")
    private Date passwordExpiryDate;

    // SocialX Email Verification
    @Column(name = "X_email_verified")
    private boolean emailVerified = false;
    
    @Column(name = "X_email_verification_token")
    private String emailVerificationToken;
    
    @Column(name = "X_email_token_expiry")
    private Date emailTokenExpiry;

    // Instagram Integration Fields
    @Column(name = "IG_username")
    private String instagramUsername;
    
    @Column(name = "IG_access_token", length = 1000)
    private String instagramAccessToken;
    
    @Column(name = "IG_token_expiry")
    private Date instagramTokenExpiry;
    
    @Column(name = "IG_refresh_token", length = 1000)
    private String instagramRefreshToken;
    
    @Column(name = "IG_last_sync")
    private Date instagramLastSync;

    // Add SocialX Token Fields
    @Column(name = "X_access_token", length = 1000)
    private String socialXAccessToken;
    
    @Column(name = "X_refresh_token", length = 1000)
    private String socialXRefreshToken;
    
    @Column(name = "X_token_expiry")
    private Date socialXTokenExpiry;
    
    @Column(name = "X_token_issued")
    private Date socialXTokenIssued;

    // Default constructor required by JPA.
    public User() {}

    // Basic getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // UserDetails interface implementations
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null || roles.isEmpty()) {
            return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (lockTime == null) {
            return true;
        }
        // Check if lock time has passed (e.g., 24 hours)
        long lockTimeInMillis = 24 * 60 * 60 * 1000; // 24 hours
        return new Date().getTime() - lockTime.getTime() > lockTimeInMillis;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (passwordExpiryDate == null) {
            return true;
        }
        return new Date().before(passwordExpiryDate);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // Instagram-specific methods
    public boolean isInstagramConnected() {
        return instagramUsername != null && 
               instagramAccessToken != null && 
               isInstagramTokenValid();
    }

    public boolean isInstagramTokenValid() {
        return instagramTokenExpiry != null && 
               instagramAccessToken != null && 
               new Date().before(instagramTokenExpiry);
    }

    // New getters and setters for Instagram fields
    public String getInstagramUsername() {
        return instagramUsername;
    }

    public void setInstagramUsername(String instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    public String getInstagramAccessToken() {
        return instagramAccessToken;
    }

    public void setInstagramAccessToken(String instagramAccessToken) {
        this.instagramAccessToken = instagramAccessToken;
    }

    public Date getInstagramTokenExpiry() {
        return instagramTokenExpiry;
    }

    public void setInstagramTokenExpiry(Date instagramTokenExpiry) {
        this.instagramTokenExpiry = instagramTokenExpiry;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    // Add setters for security fields
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }

    public void setPasswordExpiryDate(Date passwordExpiryDate) {
        this.passwordExpiryDate = passwordExpiryDate;
    }

    // Add setters for Instagram fields
    public void setInstagramRefreshToken(String instagramRefreshToken) {
        this.instagramRefreshToken = instagramRefreshToken;
    }

    public void setInstagramLastSync(Date instagramLastSync) {
        this.instagramLastSync = instagramLastSync;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public void setEmailTokenExpiry(Date emailTokenExpiry) {
        this.emailTokenExpiry = emailTokenExpiry;
    }

    public void setFailedAttempt(int failedAttempt) {
        this.failedAttempt = failedAttempt;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    // Add getters and setters for new fields
    public String getSocialXAccessToken() {
        return socialXAccessToken;
    }

    public void setSocialXAccessToken(String socialXAccessToken) {
        this.socialXAccessToken = socialXAccessToken;
    }

    public String getSocialXRefreshToken() {
        return socialXRefreshToken;
    }

    public void setSocialXRefreshToken(String socialXRefreshToken) {
        this.socialXRefreshToken = socialXRefreshToken;
    }

    public Date getSocialXTokenExpiry() {
        return socialXTokenExpiry;
    }

    public void setSocialXTokenExpiry(Date socialXTokenExpiry) {
        this.socialXTokenExpiry = socialXTokenExpiry;
    }

    public Date getSocialXTokenIssued() {
        return socialXTokenIssued;
    }

    public void setSocialXTokenIssued(Date socialXTokenIssued) {
        this.socialXTokenIssued = socialXTokenIssued;
    }

    // Add helper method to check SocialX token validity
    public boolean isSocialXTokenValid() {
        return socialXTokenExpiry != null && 
               socialXAccessToken != null && 
               new Date().before(socialXTokenExpiry);
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