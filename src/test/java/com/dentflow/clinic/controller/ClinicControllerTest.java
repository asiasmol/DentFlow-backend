package com.dentflow.clinic.controller;

import com.dentflow.clinic.model.Clinic;
import com.dentflow.clinic.model.ClinicRequest;
import com.dentflow.clinic.service.ClinicService;
import com.dentflow.patient.model.Patient;
import com.dentflow.user.model.Role;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
class ClinicControllerTest {

    @Mock
    private ClinicService clinicService;

    @Mock
    private Authentication authentication;

    private ClinicController clinicController;

    private User testUser1;
    private User testUser2;
    private Clinic clinic1;
    private Clinic clinic2;

    @Before
    public void setUp() {
        String userName1 = "John1";
        String lastName1 = "Doe1";
        String email1 = "test1@example.com";
        String password1 = "password1";

        String userName2 = "John2";
        String lastName2 = "Doe2";
        String email2 = "test2@example.com";
        String password2 = "password2";

        testUser1.setFirstName(userName1);
        testUser1.setLastName(lastName1);
        testUser1.setEmail(email1);
        testUser1.setPassword(password1);

        testUser2.setFirstName(userName2);
        testUser2.setLastName(lastName2);
        testUser2.setEmail(email2);
        testUser2.setPassword(password2);

        String clinicName1 = "testClinic1";
        String clinicAddress1 = "testAddress1";
        String clinicName2 = "testClinic2";
        String clinicAddress2 = "testAddress2";

        clinic1.setName(clinicName1);
        clinic2.setName(clinicName2);

        clinicController = new ClinicController(clinicService);
        User testUser1 = new User();
        User testUser2 = new User();
    }

    @Test
    public void testGetClinicByUser() {
        Set<Clinic> testClinics = new HashSet<>();
        testClinics.add(clinic1);
        testClinics.add(clinic2);
        Mockito.when(authentication.getPrincipal()).thenReturn(testUser1);
        Mockito.when(clinicService.getAllUserClinicWhereWork(testUser1.getEmail())).thenReturn(testClinics);
        Set<Clinic> result = clinicController.getClinicByUser(authentication);
        Assert.assertEquals(result, testClinics);
    }

//    @Test
//    public void testRegisterClinic() {
//        ClinicRequest testRequest = new ClinicRequest("testClinic", "testAddress");
//        Mockito.when(authentication.getPrincipal()).thenReturn(testUser1);
//        clinicController.registerClinic(testRequest, authentication);
//        Mockito.verify(clinicService).registerClinic(testRequest, testUser1);
//    }

    @Test
    public void testGetMyClinic() {
        Clinic testClinic = clinic1;
        Mockito.when(authentication.getPrincipal()).thenReturn(testUser1);
        Mockito.when(clinicService.getMyClinic(testUser1.getEmail())).thenReturn(testClinic);
        Clinic result = clinicController.get(authentication);
        Assert.assertEquals(result, testClinic);
    }

    @Test
    public void testGetPersonnel() {
        Set<User> testPersonnel = new HashSet<>();
        testPersonnel.add(testUser1);
        testPersonnel.add(testUser1);
        Mockito.when(authentication.getPrincipal()).thenReturn(testUser1);
        Mockito.when(clinicService.getPersonnel(testUser1.getEmail())).thenReturn(testPersonnel);
        Set<User> result = clinicController.getPersonnel(authentication);
        Assert.assertEquals(result, testPersonnel);
    }

    @Test
    public void testAddEmployee() {
        UserRequest testRequest = new UserRequest("Jane", "Doe", "jane@example.com", Role.USER);
        Mockito.when(authentication.getPrincipal()).thenReturn(testUser1);
        clinicController.addEmployee(testRequest, authentication);
        Mockito.verify(clinicService).addEmployee(testUser1.getEmail(), testRequest);
    }

//    @Test
//    public void testGetPatients() {
//        Set<Patient> testPatients = new HashSet<>();
//        testPatients.add(new Patient("patient1@example.com", "John", "Doe", new HashSet<>()));
//        testPatients.add(new Patient("patient2@example.com", "Jane", "Doe", new HashSet<>()));
//        ClinicRequest testRequest = new ClinicRequest(1L);
//        Mockito.when(authentication.getPrincipal()).thenReturn(testUser1);
//        Mockito.when(clinicService.getPatients(testUser1.getEmail(), testRequest.getClinicId())).thenReturn(testPatients);
//        Set<Patient> result = clinicController.getPatients(authentication, testRequest);
//        Assert.assertEquals(result, testPatients);
//    }
}