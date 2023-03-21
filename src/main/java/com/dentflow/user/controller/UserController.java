package com.dentflow.user.controller;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserType;
import com.dentflow.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void addUser(
            @RequestParam("firstName")String firstName,
            @RequestParam("lastName")String lastName,
            @RequestParam("email")String email,
            @RequestParam("type") UserType type,
            @RequestParam("password")String password
    ){
        userService.addUser(firstName,lastName,email,type,password);
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
    @GetMapping("/{id}/all_clinics")
    public ResponseEntity<Set<Clinic>> getAllClinics(@PathVariable Long id){
        if (!userService.getAllClinics(id).isEmpty()){
            return ResponseEntity.ok(userService.getAllClinics(id));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
