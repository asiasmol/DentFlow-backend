package com.dentflow.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/user")
public class UserController {

    @GetMapping("/{id}")
    public void getUser(){

    }
    @GetMapping("/{id}/all_clinics")
    public void getAllClinics(){

    }
}
