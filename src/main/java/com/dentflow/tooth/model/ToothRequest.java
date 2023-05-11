package com.dentflow.tooth.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToothRequest {
    private Long clinicId;
    private Long patientId;
    private ToothRequestModel tooth;


}
