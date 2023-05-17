package com.dentflow.user.model;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String pesel;
    private String birthDate;
    private String email;
    private Role role;

    public static User toEntity(UserRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .pesel(request.getPesel())
                .birthDate(convertStringtoData(request.getBirthDate()))
                .email(request.getEmail())
                .build();
    }
    private static LocalDate convertStringtoData(String visitDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(visitDate, dateFormatter);
    }
}
