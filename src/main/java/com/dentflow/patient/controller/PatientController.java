package com.dentflow.patient.controller;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRequest;
import com.dentflow.patient.service.PatientService;
import com.dentflow.user.model.User;
import com.dentflow.visit.model.Visit;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{patientId}}")
    public Patient getPatientFromClinic(
            @PathVariable long patientId){
        return patientService.getPatient(patientId);
    }
    @PostMapping
    public void registerPatient(@RequestBody PatientRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        patientService.registerPatient(request, user.getEmail());
    }

    @GetMapping
    public Set<Visit> getVisitHistory(@RequestBody PatientRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return patientService.getPatientVisitHistory(request, user.getEmail());
    }
}
