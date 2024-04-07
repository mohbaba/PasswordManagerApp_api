package com.passwordManager.api.dtos.responses.loginInfoResponses;

import lombok.Data;

@Data
public class GetLoginInfoResponse {
    private String savedUsername;
    private String savedPassword;
    private String savedWebsite;
}
