package com.passwordManager.api.dtos.responses.loginInfoResponses;

import lombok.Data;

@Data
public class LoginInfoResponse {
    private String id;
    private boolean successfullyAdded;
    private String savedUsername;
    private String savedWebsite;
}
