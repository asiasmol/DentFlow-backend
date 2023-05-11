package com.dentflow.tooth.controller;

import com.dentflow.exception.ApiRequestException;
import com.dentflow.patient.model.PatientRequest;
import com.dentflow.patient.service.PatientService;
import com.dentflow.tooth.model.Tooth;
import com.dentflow.tooth.model.ToothRequest;
import com.dentflow.tooth.service.ToothService;
import com.dentflow.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/teeth")
public class ToothController {
    private final ToothService toothService;
    private final PatientService patientService;
    public ToothController(ToothService toothService, PatientService patientService) {
        this.toothService = toothService;
        this.patientService = patientService;
    }
    @GetMapping
    public Set<Tooth> getAllTooth(@RequestBody PatientRequest patientRequest, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Long patientId = patientRequest.getPatientId();

        if(!patientService.checkIfPatientExist(patientId)) {
            throw new ApiRequestException("Cannot find patient with that id" + patientId);
        }

        return toothService.getAllToothByPatientId(user.getEmail(), patientRequest.getClinicId(), patientId);
    }
    @PatchMapping("/status")
    public void updateStatus(@RequestBody ToothRequest toothRequest, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        toothService.updateStatus(toothRequest,user.getEmail());
    }
    @PatchMapping("/description")
    public void updateDescription(@RequestBody ToothRequest toothRequest, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        toothService.updateDescription(toothRequest,user.getEmail());
    }
    @PatchMapping("/status")
    public void updateStatus(@RequestBody ToothRequest toothRequest, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        toothService.updateStatus(toothRequest,user.getEmail());
    }
    @PatchMapping("/description")
    public void updateDescription(@RequestBody ToothRequest toothRequest, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        toothService.updateDescription(toothRequest,user.getEmail());
    }
}
