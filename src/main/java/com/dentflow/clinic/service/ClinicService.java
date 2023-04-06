package com.dentflow.clinic.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.user.model.GetUserResponse;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import com.dentflow.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Data
@AllArgsConstructor
public class ClinicService {

    private UserRepository userRepository;
    private ClinicRepository clinicRepository;
    private UserService userService;

    public void registerClinic(String name,User user) {
        Clinic clinic = new Clinic(name, user);
        clinicRepository.save(clinic);
        user.setMyClinic(clinic);
        userRepository.save(user);
    }

    public Set<Clinic> getAllUserClinicWhereWork(String email) {
        return userService.getAllClinicsWhereWork(email);
    }

//    public List<Patient> getAllPatient(long clinicId) {
//        return null;
//    }
//
//    public void addPatient(long clinicId, long patientId) {
//    }
//    public Clinic getClinicById(long clinicId) {
//        if(clinicRepository.findById(clinicId).isPresent()){
//            return clinicRepository.findById(clinicId).get();
//        }
//        return null;
//    }

    public void addEmployee(String myEmail, String email) {
        // TODO
       Clinic clinic = userService.getMyClinic(myEmail);
       clinic.addEmployee(userService.getUser(email));
       userRepository.findByEmail(email).get().addClinic(clinic);
       userRepository.save(userRepository.findByEmail(email).get());
       clinicRepository.save(clinic);
    }


    public Set<User> getPersonnel(Long clinicId) {
        Optional<Clinic> clinic = clinicRepository.findById(clinicId);
        if (clinic.isPresent()){
            return clinic.get().getPersonnel();
        } return null;
    }
//    public void deleteClinic(Long clinicId){
//        clinicRepository.delete(clinicRepository.findById(clinicId).get());
//    }
//
//    public void removeEmployee(Long userId, Long clinicId){
//        clinicRepository.findById(clinicId).get().removeEmployee(userService.getUser(userId));
//    }

    public Clinic getMyClinic(String email) {
        return userService.getMyClinic(email);
    }
}
