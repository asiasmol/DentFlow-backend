package com.dentflow.patient.controller;

import com.dentflow.patient.model.Patient;
import com.dentflow.patient.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/register_patient")
    public void registerPatientToClinic(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("clinicId") long clinicId) {
        patientService.registerPatient(firstName, lastName, email, clinicId);
    }
    @GetMapping("/{patientId}")
    public Patient getPatientFromClinic(
            @PathVariable long patientId){
        return patientService.getPatient(patientId);
    }
    @GetMapping("/all/{clinicId}")
    public List<Patient> getAllPatientsFromClinic(
            @PathVariable long clinicId) {
        return patientService.getAllPatientsFromClinic(clinicId);
    }
}
