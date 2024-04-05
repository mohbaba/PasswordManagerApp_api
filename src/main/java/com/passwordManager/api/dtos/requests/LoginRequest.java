package com.passwordManager.api.dtos.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }
}
