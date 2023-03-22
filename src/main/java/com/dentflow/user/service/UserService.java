package com.dentflow.user.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import com.dentflow.user.model.UserType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    public Set<Clinic> getAllClinics(Long userId) {
        return null;
    }

    public void deleteUser(Long userId){
        userRepository.delete(userRepository.findById(userId).get());
    }


}
