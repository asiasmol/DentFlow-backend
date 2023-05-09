package com.dentflow.tooth.model;

import com.dentflow.description.model.Description;
import com.dentflow.patient.model.Patient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "teeth")
public class Tooth {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toothId;

    private int number;

    @OneToMany
    private List<Description> descriptions;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private Boolean forObservation;
    private Boolean caries;
    private Boolean secondaryCaries;
    private Boolean filling;
    private Boolean prostheticCrown;
    private Boolean channelsFilledCorrectly;
    private Boolean channelNotCompleted;
    private Boolean periapicalChange;
    private Boolean crownRootInsert;
    private Boolean supragingivalCalculus;
    private Boolean subgingivalCalculus;
    private Boolean impactedTooth;
    private Boolean noTooth;
    private Boolean microdonticTooth;
    private Boolean developmentalDefect;
    private Boolean pathologicalClash;
}


