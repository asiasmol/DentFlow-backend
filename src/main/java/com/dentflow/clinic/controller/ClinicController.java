package com.dentflow.clinic.controller;

import com.dentflow.auth.model.AuthenticationResponses;
import com.dentflow.auth.model.RegisterRequest;
import com.dentflow.auth.service.AuthenticationService;
import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRequest;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.exception.ApiRequestException;
import com.dentflow.patient.model.Patient;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRequest;
import com.dentflow.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    private final ClinicService clinicService;
    private final AuthenticationService authService;
    private final UserService userService;


    public ClinicController(ClinicService clinicService, AuthenticationService authService, UserService service) {
        this.clinicService = clinicService;
        this.authService = authService;
        this.userService = service;
    }

    @GetMapping("/myClinics")
    public Set<Clinic> getClinicByUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return clinicService.getAllUserClinicWhereWork(user.getEmail());
    }

    @PostMapping
    public void registerClinic(
            @RequestBody  ClinicRequest clinicRequest) {

        RegisterRequest ownerRegistrationRequest = new RegisterRequest(
                clinicRequest.getOwnerName(),
                clinicRequest.getOwnerLastname(),
                clinicRequest.getEmail(),
                clinicRequest.getPassword());

        authService.registerOwner(ownerRegistrationRequest);
        clinicService.registerClinic(clinicRequest, userService.getUser(clinicRequest.getEmail()));
    }
    @GetMapping("/myClinic")
    public Clinic get(Authentication authentication) {
        User user  = (User) authentication.getPrincipal();
        return clinicService.getMyClinic(user.getEmail());
    }

    @GetMapping("/personnel")
    public Set<User> getPersonnel(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return clinicService.getPersonnel(user.getEmail());
    }

    @PatchMapping("/personnel")
    public void addEmployee(@RequestBody UserRequest userRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        clinicService.addEmployee(user.getEmail(), userRequest);
    }

    @DeleteMapping("/personnel")
    public void deleteEmployee(@RequestBody UserRequest userRequest, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        clinicService.deleteEmployee(user.getEmail(), userRequest);
    }

    @GetMapping("/patients")
    public Set<Patient> getPatients(Authentication authentication, ClinicRequest clinicRequest){
        User user = (User) authentication.getPrincipal();
        Long clinicId = clinicRequest.getClinicId();

        if(!clinicService.checkIfClinicExists(clinicId)){
            throw new ApiRequestException("Cannot find clinic with that id" + clinicId);
        }

        return clinicService.getPatients(user.getEmail(), clinicId);
    }

    @GetMapping("/doctors")
    public Set<User> getDoctors(Authentication authentication, ClinicRequest clinicRequest){
        User user = (User) authentication.getPrincipal();
        Long clinicId = clinicRequest.getClinicId();

        if(!clinicService.checkIfClinicExists(clinicId)){
            throw new ApiRequestException("Cannot find clinic with that id" + clinicId);
        }

        return clinicService.getDoctors(user.getEmail(), clinicId);
    }


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
