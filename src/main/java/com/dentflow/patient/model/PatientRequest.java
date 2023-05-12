package com.dentflow.patient.model;

import com.dentflow.user.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {
    private Long clinicId;
    private Long patientId;
    private String firstName;
    private String lastName;
    private String pesel;
    private String birthDate;
    private String phoneNumber;
    private String email;
    public static Patient toEntity(PatientRequest request) {
        return Patient.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .pesel(request.getPesel())
                .birthDate(convertStringtoData(request.getBirthDate()))
                .phoneNumber(request.getPhoneNumber())
                .teeth(new HashSet<>())
                .build();
    }
    private static LocalDate convertStringtoData(String visitDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(visitDate, dateFormatter);
    }
}
