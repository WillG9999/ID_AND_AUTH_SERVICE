package com.SocialX.ID_AUTH_SERVICE;

public class JwtAuthenticationResponse {
    private String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    // Getter for token
    public String getToken() {
        return token;
    }
}
