package com.dentflow.emailSender.controller;

import com.dentflow.emailSender.model.ResetToken;
import com.dentflow.emailSender.model.ResetTokenRequest;
import com.dentflow.emailSender.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resetPassword")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PatchMapping
    public void ResetPassword(@RequestBody ResetTokenRequest request) {
        emailService.ResetPassword(request);
    }
    @PostMapping
    public void CreateToken( @RequestBody ResetTokenRequest request) {
        emailService.CreteToken(request.getEmail());
    }
}
