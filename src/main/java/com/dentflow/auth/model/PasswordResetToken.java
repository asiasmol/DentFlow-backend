package com.dentflow.auth.model;

import com.dentflow.user.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public class PasswordResetToken {
    private String token;
    private User user;

    public PasswordResetToken(User user, String token ) {
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }



}
