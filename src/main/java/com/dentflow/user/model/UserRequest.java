package com.dentflow.user.model;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String email;

    public static User toEntity(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .build();
    }
}
