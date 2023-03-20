package com.dentflow.user.controller;

import com.dentflow.user.model.User;
import com.dentflow.user.model.UserType;
import com.dentflow.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController("/api/user")
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
    public void getAllClinics(){

    }
}
