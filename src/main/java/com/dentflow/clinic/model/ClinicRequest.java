package com.dentflow.clinic.model;

import com.dentflow.user.model.User;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClinicRequest {
    private Long clinicId;
    private String clinicName;
    private String ownerName;
    private String ownerLastname;
    @Email
    private String email;
    private String city;
    private String address;
    private String password;

    public static Clinic toEntity(ClinicRequest request, User user) {
        return Clinic.builder()
                .name(request.getClinicName())
                .owner(user)
                .city(request.city)
                .address(request.address)
                .build();
    }
}
