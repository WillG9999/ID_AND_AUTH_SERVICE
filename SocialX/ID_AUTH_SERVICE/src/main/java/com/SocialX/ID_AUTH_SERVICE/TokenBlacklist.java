package com.SocialX.ID_AUTH_SERVICE;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "token_blacklist")
public class TokenBlacklist {
    
    @Id
    @Column(length = 500)  // JWT tokens can be long
    private String token;   // The actual JWT token that's been blacklisted
    
    @Column(nullable = false)
    private Date blacklistedAt;  // When the token was blacklisted
    
    @Column(length = 100)
    private String reason;  // Why the token was blacklisted (e.g., "user_logout", "security_breach")
    
    @Column(nullable = false)
    private Date expiresAt;  // When the token naturally expires
    
    @Column(length = 50)
    private String blacklistedBy;  // Username or system ID that blacklisted the token

    // Default constructor required by JPA
    public TokenBlacklist() {}
    
    // Constructor for easy object creation
    public TokenBlacklist(String token, String reason, Date expiresAt, String blacklistedBy) {
        this.token = token;
        this.reason = reason;
        this.blacklistedAt = new Date();
        this.expiresAt = expiresAt;
        this.blacklistedBy = blacklistedBy;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getBlacklistedAt() {
        return blacklistedAt;
    }

    public void setBlacklistedAt(Date blacklistedAt) {
        this.blacklistedAt = blacklistedAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getBlacklistedBy() {
        return blacklistedBy;
    }

    public void setBlacklistedBy(String blacklistedBy) {
        this.blacklistedBy = blacklistedBy;
    }
} 