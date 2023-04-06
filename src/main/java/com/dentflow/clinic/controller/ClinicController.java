package com.dentflow.clinic.controller;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.user.model.GetUserResponse;
import com.dentflow.user.model.User;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    private ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/whereWork")
    public Set<Clinic> getClinicByUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return clinicService.getAllUserClinicWhereWork(user.getEmail());
    }

    @GetMapping("/myClinic")
    public Clinic get(Authentication authentication) {
        User user  = (User) authentication.getPrincipal();
        return clinicService.getMyClinic(user.getEmail());
    }

//    @GetMapping("/{clinicId}")
//    public Clinic getClinicById(@PathVariable long clinicId) {
//        return clinicService.getClinicById(clinicId);
//    }
    @Transactional
    @PostMapping
    public void registerClinic(
            @RequestParam String name,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        clinicService.registerClinic(name ,user);
    }

//    @GetMapping("/{clinicId}/personnel")
//    public Set<User> getPersonnel(@PathVariable("clinicId") Long clinicId) {
//        return clinicService.getPersonnel(clinicId);
//    }

//    @GetMapping("/personnel/{userId}")
//    public void getEmployee() {
//    }

    @Transactional
    @PatchMapping("/personnel")
    public void addEmployee(@RequestParam String email, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        clinicService.addEmployee(user.getEmail(), email);

    }


//    @PostMapping("/{clinicId}/patient/{patientId}/add")
//    public void addPatient(
//            @PathVariable long clinicId,
//            @PathVariable long patientId
//    ) {
//        clinicService.addPatient(clinicId, patientId);
//    }

//    @GetMapping("/{clinicId}/patient/all")
//    public List<Patient> getAllPatientsFromClinic(
//            @PathVariable long clinicId) {
//        return clinicService.getAllPatient(clinicId);
//    }
//
//    @Transactional
//    @DeleteMapping("/{clinicId}")
//    public void deleteClinic(@PathVariable Long clinicId){
//        clinicService.deleteClinic(clinicId);
//    }
//
//    @Transactional
//    @DeleteMapping("/{clinicId}/personnel/{userId}")
//    public void removeUser(
//            @PathVariable("clinicId") Long clinicId,
//            @PathVariable("userId") Long userId){
//        clinicService.removeEmployee(userId, clinicId);
//    }

}
