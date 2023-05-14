package com.dentflow.emailSender.service;

import com.dentflow.emailSender.model.ResetToken;
import com.dentflow.emailSender.model.ResetTokenRepository;
import com.dentflow.emailSender.model.ResetTokenRequest;
import com.dentflow.patient.model.Patient;
import com.dentflow.patient.model.PatientRepository;
import com.dentflow.user.model.User;
import com.dentflow.user.model.UserRepository;
import com.dentflow.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final ResetTokenRepository resetTokenRepository;

    public EmailService(JavaMailSender javaMailSender, PasswordEncoder passwordEncoder, UserService userService, UserRepository userRepository, PatientRepository patientRepository, ResetTokenRepository resetTokenRepository) {
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.resetTokenRepository = resetTokenRepository;
        this.userService = userService;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);
        javaMailSender.send(mail);
    }

    public void CreteResetPasswordToken(String email) {
        String token = UUID.randomUUID().toString();
        resetTokenRepository.save(ResetToken.builder().token(token).email(email).build());
//        // Wysyłanie maila z linkiem resetującym hasło
        String resetPasswordUrl = "http://localhost:3000/reset-password?token=" + token;
        String subject = "Reset hasła";
        String body = "Aby zresetować hasło kliknij w poniższy link:\n" + resetPasswordUrl;
        sendEmail(email, subject, body);
    }

    public void CreteResetEmailToken(String email) {
        String token = UUID.randomUUID().toString();
        resetTokenRepository.save(ResetToken.builder().token(token).email(email).build());
//        // Wysyłanie maila z linkiem resetującym hasło
        String resetPasswordUrl = "http://localhost:3000/reset-email?token=" + token;
        String subject = "Zmiana Maila";
        String body = "Aby Zmienić maila kliknij w poniższy link:\n" + resetPasswordUrl;
        sendEmail(email, subject, body);
    }
    public void ResetPassword(ResetTokenRequest request) {
        ResetToken resetToken = resetTokenRepository.findByToken(request.getToken()).orElseThrow(() -> new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "valid token "));
        User user = userService.getUser(resetToken.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        resetTokenRepository.delete(resetToken);
    }


    public void ResetEmail(ResetTokenRequest request) {
        ResetToken resetToken = resetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "valid token "));
        User user = userService.getUser(resetToken.getEmail());
        User takenUser =userService.getUser(request.getEmail());
        if (takenUser != null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        user.setEmail(request.getEmail());
        userRepository.save(user);
        Set<Patient> myPatientsAccounts =  user.getMyPatientsAccounts();
        myPatientsAccounts.forEach(myPatientsAccount->{
            myPatientsAccount.setEmail(request.getEmail());
            patientRepository.save(myPatientsAccount);
        });
        resetTokenRepository.delete(resetToken);
    }
}
