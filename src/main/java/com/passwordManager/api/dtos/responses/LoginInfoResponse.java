package com.passwordManager.api.dtos.responses;

import lombok.Data;

@Data
public class LoginInfoResponse {
    private String id;
    private boolean successfullyAdded;
//    private String savedPassword;
    private String savedUsername;
    private String savedWebsite;
}
