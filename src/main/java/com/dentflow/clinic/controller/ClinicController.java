package com.dentflow.clinic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/clinic")
public class ClinicController {

    @PostMapping("/create")
    public void createClinic(){
    }
    @GetMapping("/personnel/all")
    public void getPersonnel(){
    }
    @GetMapping("/personnel/{id}")
    public void getEmployee(){
    }
    @PostMapping("/personnel/add")
    public void addEmployee(){
    }
}
