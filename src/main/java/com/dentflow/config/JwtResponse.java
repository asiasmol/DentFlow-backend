package com.dentflow.config;

import com.dentflow.user.model.Role;

import java.util.Set;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private Set<Role> roles;

    public JwtResponse(String accessToken, String email, Set<Role> roles) {
        this.token = accessToken;
        this.email = email;
        this.roles = roles;
    }
}
