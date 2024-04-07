package com.passwordManager.api.dtos.requests.loginInfoRequests;

import lombok.Data;

@Data
public class GetLoginInfoRequest {
    private String loginInfoId;
    private String username;
    private String password;

}
