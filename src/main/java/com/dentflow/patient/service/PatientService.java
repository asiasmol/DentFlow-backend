package com.dentflow.patient.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRepository;
import com.dentflow.patient.model.PatientRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ClinicRepository clinicRepository;
    private final ClinicService clinicService;


    public PatientService(PatientRepository patientRepository, ClinicRepository clinicRepository, ClinicService clinicService) {
        this.patientRepository = patientRepository;
        this.clinicRepository = clinicRepository;
        this.clinicService = clinicService;
    }

    public void registerPatient(PatientRequest request) {
        Patient patient = PatientRequest.toEntity(request);
        Clinic clinic = clinicService.getClinicById(request.getClinicId());
        patientRepository.save(patient);
        clinic.addPatient(patient);
        clinicRepository.save(clinic);
    }

    public Patient getPatient(long patientId) {
        return patientRepository.findById(patientId).get();
    }

    public List<Patient> getAllPatientsFromClinic(long clinicId) {
       return null;
    }
}
