package com.dentflow.user.controller;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserType;
import com.dentflow.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
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
    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }
    @GetMapping("/{userId}/all_clinics")
    public ResponseEntity<Set<Clinic>> getAllClinics(@PathVariable Long userId){
        if (!userService.getAllClinics(userId).isEmpty()) return ResponseEntity.ok(userService.getAllClinics(userId));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Transactional
    @DeleteMapping("/{userId}")
    public void removeUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
}
