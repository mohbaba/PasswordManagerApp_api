package com.passwordManager.api.dtos.requests.loginInfoRequests;

import lombok.Data;

@Data
public class LoginInfoRequest {
    private String usernameToBeSaved;
    private String username;
    private String passwordToBeSaved;
    private String website;
}
