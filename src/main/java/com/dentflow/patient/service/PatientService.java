package com.dentflow.patient.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRepository;
import com.dentflow.patient.model.PatientRequest;
import com.dentflow.tooth.model.Tooth;
import com.dentflow.tooth.model.ToothRepository;
import com.dentflow.user.service.UserService;
import com.dentflow.visit.model.Visit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ClinicRepository clinicRepository;
    private final ToothRepository toothRepository;
    private final UserService userService;


    public PatientService(PatientRepository patientRepository, ClinicRepository clinicRepository, ToothRepository toothRepository, ClinicService clinicService, UserService userService) {
        this.patientRepository = patientRepository;
        this.clinicRepository = clinicRepository;
        this.toothRepository = toothRepository;
        this.userService = userService;
    }

    public void registerPatient(PatientRequest request, String email) {
        Patient patient = PatientRequest.toEntity(request);
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> c.getId() == request.getClinicId())
                        .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"));
        patientRepository.save(patient);
        for(int i = 1; i <= 32; i++){
            patient.addTooth(toothRepository.save(Tooth.builder().number(i).forObservation(false).caries(false).secondaryCaries(false).filling(false).prostheticCrown(false).channelsFilledCorrectly(false).channelNotCompleted(false).periapicalChange(false).crownRootInsert(false).supragingivalCalculus(false).subgingivalCalculus(false).impactedTooth(false).noTooth(false).microdonticTooth(false).developmentalDefect(false).pathologicalClash(false)
                    .patient(patient).build()));
        }
        patientRepository.save(patient);
        clinic.addPatient(patient);
        clinicRepository.save(clinic);
    }

    public Patient getPatient(long patientId) {
        return patientRepository.findById(patientId).get();
    }

    public Set<Visit> getPatientVisitHistory(Long clinicId, Long patientId, String email){
//        Patient patient = PatientRequest.toEntity(request);
        Clinic clinic = userService.getUser(email).getClinics().stream()
                .filter(c -> Objects.equals(c.getId(), clinicId)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Clinic not found"));
        return clinic.getPatients().stream().filter(p -> p.getPatientId() == patientId).findFirst().get().getVisits();
    }
    public boolean checkIfPatientExist(Long patientId) {
        return patientRepository.existsById(patientId);
    }
}
