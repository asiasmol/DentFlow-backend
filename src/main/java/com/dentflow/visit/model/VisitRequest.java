package com.dentflow.visit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitRequest {

    private Long clinicId;
    private String visitDate;
    private String visitTime;
    private String doctorEmail;
    private Long patientId;
    private String description;

    public static Visit toEntity(VisitRequest request) {
        return Visit.builder()
                .visitDate(convertStringtoData(request.getVisitDate(), request.getVisitTime()))
                .description(request.getDescription())
                .build();
    }

    private static LocalDateTime convertStringtoData(String visitDate, String visitTime){
        System.out.println(visitDate);
        System.out.println(visitTime);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(visitDate, dateFormatter);
        DateTimeFormatter timeFormatter;

        if (visitTime.length() == 4) timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        else timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime time = LocalTime.parse(visitTime, timeFormatter);
        return LocalDateTime.of(date, time);
    }
}
