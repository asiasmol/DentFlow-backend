package com.dentflow.patient.service;

import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;


    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void registerPatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient getPatient(long patientId) {
        return patientRepository.findById(patientId).get();
    }

    public List<Patient> getAllPatientsFromClinic(long clinicId) {
       return null;
    }
}
