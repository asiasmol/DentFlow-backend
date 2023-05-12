package com.dentflow.emailSender.controller;

import com.dentflow.emailSender.model.ResetTokenRequest;
import com.dentflow.emailSender.service.EmailService;
import com.dentflow.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PatchMapping("/resetPassword")
    public void ResetPassword(@RequestBody ResetTokenRequest request) {
        emailService.ResetPassword(request);
    }
    @PostMapping("/resetPassword")
    public void CreateToken( @RequestBody ResetTokenRequest request) {
        emailService.CreteResetPasswordToken(request.getEmail());
    }
}
