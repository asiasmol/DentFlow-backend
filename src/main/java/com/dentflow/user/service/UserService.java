package com.dentflow.user.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


//    public void addUser(User user) {
//        userRepository.save(user);
//    }

//    public List<User> getAll() {
//        return userRepository.findAll();
//    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).get();
    }

    public Set<Clinic> getAllClinicsWhereWork(String email) {
        return userRepository.findByEmail(email).get().getClinicsWhereWork();

    }

//    public void deleteUser(Long userId) {
//        User user = userRepository.findById(userId).get();
//        user.clearClinics();
//        userRepository.delete(user);
//    }
    public Clinic getMyClinic(String email) {
        return userRepository.findByEmail(email).get().getMyClinic();

    }
}
