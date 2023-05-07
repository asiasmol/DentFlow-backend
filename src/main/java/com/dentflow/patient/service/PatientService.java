package com.dentflow.patient.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRepository;
import com.dentflow.patient.model.PatientRequest;
import com.dentflow.tooth.model.Tooth;
import com.dentflow.user.service.UserService;
import com.dentflow.visit.model.Visit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ClinicRepository clinicRepository;
    private final ClinicService clinicService;
    private final UserService userService;


    public PatientService(PatientRepository patientRepository, ClinicRepository clinicRepository, ClinicService clinicService, UserService userService) {
        this.patientRepository = patientRepository;
        this.clinicRepository = clinicRepository;
        this.clinicService = clinicService;
        this.userService = userService;
    }

    public void registerPatient(PatientRequest request, String email) {
        Patient patient = PatientRequest.toEntity(request);
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> c.getId() == request.getClinicId())
                        .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"));
        for(int i = 1; i <= 32; i++){
            patient.getTeeth().add(Tooth.builder().number(i).patient(patient).build());
        }
        patientRepository.save(patient);
        clinic.addPatient(patient);
        clinicRepository.save(clinic);
    }

    public Patient getPatient(long patientId) {
        return patientRepository.findById(patientId).get();
    }

    public List<Patient> getAllPatientsFromClinic(long clinicId) {
       return null;
    }

    public Set<Visit> getPatientVisitHistory(PatientRequest request, String email){
        Patient patient = PatientRequest.toEntity(request);
        Clinic clinic = userService.getUser(email).getClinics().stream()
                .filter(c -> c.getId() == request.getClinicId()).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"));
        return clinic.getPatients().stream().filter(p -> p == patient).findFirst().get().getVisits();
    }

}
