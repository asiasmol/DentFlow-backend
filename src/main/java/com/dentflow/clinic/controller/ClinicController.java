package com.dentflow.clinic.controller;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.service.ClinicService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinic")
public class ClinicController {

    private ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/all")
    public List<Clinic> getClinic(){
        return clinicService.getAllClinic();
    }
    @PostMapping("/create")
    public void createClinic(
            @RequestParam("name")String name,
            @RequestParam("password")String password
    ){
        clinicService.createClinic(name,password);
    }
    @GetMapping("/personnel/all")
    public void getPersonnel(){
    }
    @GetMapping("/personnel/{userId}")
    public void getEmployee(){
    }
    @PostMapping("/personnel/add")
    public void addEmployee(){
    }
}
