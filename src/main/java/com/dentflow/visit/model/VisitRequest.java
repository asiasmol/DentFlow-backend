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
    private Long visitId;
    private Long clinicId;
    private String visitDate;
    private String visitTime;
    private String doctorEmail;
    private Long patientId;
    private String doctorDescription;
    private String receptionistDescription;
    private Type type;
    private int lengthOfTheVisit;

    public static Visit toEntity(VisitRequest request) {
        return Visit.builder()
                .type(request.getType())
                .lengthOfTheVisit(request.getLengthOfTheVisit())
                .visitDate(convertStringtoData(request.getVisitDate(), request.getVisitTime()))
                .doctorDescription(request.getDoctorDescription())
                .receptionistDescription(request.getReceptionistDescription())
                .build();
    }

    private static LocalDateTime convertStringtoData(String visitDate, String visitTime){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(visitDate, dateFormatter);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(visitTime, timeFormatter);

        return LocalDateTime.of(date, time);
    }
}
