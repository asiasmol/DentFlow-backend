package com.dentflow.clinic.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class ClinicService {
    private ClinicRepository clinicRepository;

    public void createClinic(String name,String password){
        clinicRepository.save(new Clinic(name,password));
    }

    public List<Clinic> getAllClinic(){
        return clinicRepository.findAll();
    }
}
