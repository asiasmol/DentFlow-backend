package com.dentflow.tooth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToothModel {
    private int number;
    private String description;
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
