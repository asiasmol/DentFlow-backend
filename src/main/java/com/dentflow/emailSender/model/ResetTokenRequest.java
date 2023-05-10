package com.dentflow.emailSender.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetTokenRequest {
    private String email;
    private String token;
    private String password;
}
