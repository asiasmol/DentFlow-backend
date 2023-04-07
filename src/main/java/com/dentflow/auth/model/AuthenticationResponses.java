package com.dentflow.auth.model;

import com.dentflow.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponses {
    private String email;
    private Set<Role> roles;
    private String token;
}
