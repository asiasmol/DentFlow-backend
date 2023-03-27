package com.dentflow.clinic.controller;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.patient.model.Patient;
import com.dentflow.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/clinics")
@CrossOrigin
public class ClinicController {

    private ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/all")
    public List<Clinic> getClinic() {
        return clinicService.getAllClinic();
    }

    @GetMapping("/{clinicId}")
    public Clinic getClinicById(@PathVariable long clinicId) {
        return clinicService.getClinicById(clinicId);
    }

    @Transactional
    @PostMapping("/register")
    public void registerClinic(
            @RequestBody Clinic clinic
    ) {
        clinicService.registerClinic(clinic);
    }


    @GetMapping("/{clinicId}/personnel")
    public Set<User> getPersonnel(@PathVariable("clinicId") Long clinicId) {
        return clinicService.getPersonnel(clinicId);
    }

    @GetMapping("/personnel/{userId}")
    public void getEmployee() {
    }

    @Transactional
    @PatchMapping("/{clinicId}/personnel/{userId}")
    public ResponseEntity<?> addEmployee(@PathVariable("clinicId") Long clinicId, @PathVariable("userId") Long userId) {
        if (clinicService.addEmployee(clinicId, userId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{clinicId}/patient/{patientId}/add")
    public void addPatient(
            @PathVariable long clinicId,
            @PathVariable long patientId
    ) {
        clinicService.addPatient(clinicId, patientId);
    }

    @GetMapping("/{clinicId}/patient/all")
    public List<Patient> getAllPatientsFromClinic(
            @PathVariable long clinicId) {
        return clinicService.getAllPatient(clinicId);
    }

    @Transactional
    @DeleteMapping("/{clinicId}")
    public void deleteClinic(@PathVariable Long clinicId){
        clinicService.deleteClinic(clinicId);
    }

    @Transactional
    @DeleteMapping("/{clinicId}/personnel/{userId}")
    public void removeUser(
            @PathVariable("clinicId") Long clinicId,
            @PathVariable("userId") Long userId){
        clinicService.removeEmployee(userId, clinicId);
    }

}
