package com.passwordManager.api.dtos.requests;

import lombok.Data;

@Data
public class LoginInfoRequest {
    private String usernameToSave;
    private String username;
    private String password;
    private String website;
}
