package com.dentflow.user.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


//    public void addUser(User user) {
//        userRepository.save(user);
//    }

    public Set<String> getAllEmails(User user) {
        Set<String> users = userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toSet());
        users.remove(user.getEmail());
//        user.getOwnedClinic().getPersonnel().stream().map(User::getEmail).collect(Collectors.toSet()).forEach(users::remove);
        return users;
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public Set<Clinic> getAllClinicsWhereWork(String email) {
        return userRepository.findByEmail(email).get().getClinics();
    }

//    public void deleteUser(Long userId) {
//        User user = userRepository.findById(userId).get();
//        user.clearClinics();
//        userRepository.delete(user);
//    }
    public Clinic getMyClinic(String email) {
        return userRepository.findByEmail(email).get().getOwnedClinic();
    }

}
