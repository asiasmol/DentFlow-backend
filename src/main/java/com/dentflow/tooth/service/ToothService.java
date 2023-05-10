package com.dentflow.tooth.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.description.model.Description;
import com.dentflow.description.model.DescriptionRepository;
import com.dentflow.patient.model.Patient;
import com.dentflow.tooth.model.Tooth;
import com.dentflow.tooth.model.ToothRepository;
import com.dentflow.tooth.model.ToothRequest;
import com.dentflow.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

@Service
public class ToothService {

    private final ToothRepository toothRepository;
    private final DescriptionRepository descriptionRepository;
    private final UserService userService;

    public ToothService(ToothRepository toothRepository, DescriptionRepository descriptionRepository, UserService userService) {
        this.toothRepository = toothRepository;
        this.descriptionRepository = descriptionRepository;
        this.userService = userService;
    }

    public Set<Tooth> getAllToothByPatientId(String email, Long clinicId, Long patientId){
        Clinic clinic = userService.getUser(email).getClinics().stream()
                .filter(c -> Objects.equals(c.getId(), clinicId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Clinic not found: "));
        return clinic.getPatients().stream()
                .filter(patient -> patient.getPatientId()== patientId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Patient not found: "))
                .getTeeth();

    }

    public void updateStatus(ToothRequest toothRequest, String email) {
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), toothRequest.getClinicId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,"Clinic not found"));
        Patient patient = clinic.getPatients().stream().filter(p -> p.getPatientId() ==  toothRequest.getPatientId())
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Patient not found: "));
        Tooth tooth =  patient.getTeeth().stream().filter(t -> t.getNumber() ==toothRequest.getTooth().getNumber())
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "tooth not found: "));
        tooth.setForObservation(toothRequest.getTooth().getForObservation());
        tooth.setCaries(toothRequest.getTooth().getCaries());
        tooth.setSecondaryCaries(toothRequest.getTooth().getSecondaryCaries());
        tooth.setFilling(toothRequest.getTooth().getFilling());
        tooth.setProstheticCrown(toothRequest.getTooth().getProstheticCrown());
        tooth.setChannelsFilledCorrectly(toothRequest.getTooth().getChannelsFilledCorrectly());
        tooth.setChannelNotCompleted(toothRequest.getTooth().getChannelNotCompleted());
        tooth.setPeriapicalChange(toothRequest.getTooth().getPeriapicalChange());
        tooth.setCrownRootInsert(toothRequest.getTooth().getCrownRootInsert());
        tooth.setSupragingivalCalculus(toothRequest.getTooth().getSupragingivalCalculus());
        tooth.setSubgingivalCalculus(toothRequest.getTooth().getSubgingivalCalculus());
        tooth.setImpactedTooth(toothRequest.getTooth().getImpactedTooth());
        tooth.setNoTooth(toothRequest.getTooth().getNoTooth());
        tooth.setMicrodonticTooth(toothRequest.getTooth().getMicrodonticTooth());
        tooth.setDevelopmentalDefect(toothRequest.getTooth().getDevelopmentalDefect());
        tooth.setPathologicalClash(toothRequest.getTooth().getPathologicalClash());
        toothRepository.save(tooth);
    }

    public void updateDescription(ToothRequest toothRequest, String email) {
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), toothRequest.getClinicId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,"Clinic not found"));
        Patient patient = clinic.getPatients().stream().filter(p -> p.getPatientId() ==  toothRequest.getPatientId())
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Patient not found: "));
        Tooth tooth =  patient.getTeeth().stream().filter(t -> t.getNumber() ==toothRequest.getTooth().getNumber())
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "tooth not found: "));
        Description description = Description.builder().description(toothRequest.getTooth().getDescription()).build();
        descriptionRepository.save(description);
        tooth.addDescription(description);
        toothRepository.save(tooth);
    }
}

