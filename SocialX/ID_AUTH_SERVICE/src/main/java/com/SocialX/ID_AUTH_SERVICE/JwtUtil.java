
package com.SocialX.ID_AUTH_SERVICE;

import io.jsonwebtoken.*;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component  // Marks this class as a Spring-managed bean
public class JwtUtil {

    // In production, store the secret securely (e.g., environment variable or vault)
    private String jwtSecret = "mySecretKey";

    // Token validity period in milliseconds (e.g., 1 hour)
    private long jwtExpirationInMs = 3600000;

    /**
     * Generate a JWT token using the provided username.
     * @param username The username to include in the token.
     * @return A signed JWT token as a String.
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        // Build and sign the JWT
        return Jwts.builder()
                .setSubject(username)             // Set the username as the subject
                .setIssuedAt(now)                 // Token creation time
                .setExpiration(expiryDate)        // Token expiration time
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // Sign with the HS512 algorithm and secret key
                .compact();
    }

    /**
     * Extract the username from a JWT token.
     * @param token The JWT token.
     * @return The username (subject) from the token.
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Validate the JWT token.
     * @param authToken The JWT token to validate.
     * @return true if valid; false otherwise.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}
