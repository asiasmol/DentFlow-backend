package com.dentflow.visit.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.patient.model.PatientRepository;
import com.dentflow.patient.service.PatientService;
import com.dentflow.user.service.UserService;
import com.dentflow.visit.model.Visit;
import com.dentflow.visit.model.VisitRepository;
import com.dentflow.visit.model.VisitRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final ClinicRepository clinicRepository;
    private final UserService userService;
    private final PatientService patientService;



    public VisitService(VisitRepository visitRepository, UserService userService, PatientService patientService, ClinicRepository clinicRepository, PatientRepository patientRepository) {
        this.visitRepository = visitRepository;
        this.userService = userService;
        this.patientService = patientService;
        this.clinicRepository = clinicRepository;
    }

    public Set<Visit> getVisitsByClinicId(String email, Long clinicId) {
        return userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), clinicId)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found: ")).getVisits();
    }

    public void addVisitsToClinic(VisitRequest visitRequest, String email) {
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), visitRequest.getClinicId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"));
        Visit visit = VisitRequest.toEntity(visitRequest);
        visit.setDoctor(userService.getUser(visitRequest.getDoctorEmail()));
        visit.setPatient(patientService.getPatient(visitRequest.getPatientId()));
        visitRepository.save(visit);
        patientService.getPatient(visitRequest.getPatientId()).getVisits().add(visit);
        clinic.addVisit(visit);
        clinicRepository.save(clinic);
    }

}
