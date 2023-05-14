package com.dentflow.visit.service;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRepository;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRepository;
import com.dentflow.patient.service.PatientService;
import com.dentflow.user.model.User;
import com.dentflow.user.service.UserService;
import com.dentflow.visit.model.Visit;
import com.dentflow.visit.model.VisitRepository;
import com.dentflow.visit.model.VisitRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final ClinicRepository clinicRepository;
    private final PatientRepository patientRepository;
    private final UserService userService;
    private final PatientService patientService;



    public VisitService(VisitRepository visitRepository, UserService userService, PatientService patientService, ClinicRepository clinicRepository, PatientRepository patientRepository, PatientRepository patientRepository1) {
        this.visitRepository = visitRepository;
        this.userService = userService;
        this.patientService = patientService;
        this.clinicRepository = clinicRepository;
        this.patientRepository = patientRepository1;
    }

    public Set<Visit> getVisitsByClinicId(String email, Long clinicId) {
        return userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), clinicId)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found: ")).getVisits();
    }

    public void addVisitsToClinic(VisitRequest visitRequest, String email) {
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), visitRequest.getClinicId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"));
        Visit visit = VisitRequest.toEntity(visitRequest);
        Patient patient = patientService.getPatientById(visitRequest.getPatientId());
        visit.setDoctor(userService.getUser(visitRequest.getDoctorEmail()));
        visit.setPatient(patient);
        visitRepository.save(visit);
        patient.addVisit(visit);
        patientRepository.save(patient);
        clinic.addVisit(visit);
        clinicRepository.save(clinic);
    }

    public void saveDoctorDescriptionToVisit(VisitRequest visitRequest, String email){
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), visitRequest.getClinicId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,"Clinic not found"));
        Visit visit = clinic.getVisits().stream().filter(v -> Objects.equals(v.getId(), visitRequest.getVisitId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Visit not found"));
        visit.setDoctorDescription(visitRequest.getDoctorDescription());
        visitRepository.save(visit);
    }

    public void saveReceptionistDescriptionToVisitToVisit(VisitRequest visitRequest, String email) {
        Clinic clinic = userService.getUser(email).getClinics().stream().filter(c -> Objects.equals(c.getId(), visitRequest.getClinicId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT,"Clinic not found"));
        Visit visit = clinic.getVisits().stream().filter(v -> Objects.equals(v.getId(), visitRequest.getVisitId())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Visit not found"));
        visit.setReceptionistDescription(visitRequest.getReceptionistDescription());
        visitRepository.save(visit);
    }

    public Set<Visit> getDoctorVisitsByClinicId(String email, Long clinicId) {
        User user = userService.getUser(email);
        return user.getClinics().stream().filter(c -> Objects.equals(c.getId(), clinicId)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found: ")).getVisits().stream().filter(visit -> Objects.equals(visit.getDoctor(), user)).collect(Collectors.toSet());
    }
    public Set<Visit> getMyVisits(String email) {
        return userService.getUser(email).getMyPatientsAccounts().stream().flatMap(patient -> patient.getVisits().stream()).collect(Collectors.toSet());
    }
}
