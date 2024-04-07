package com.passwordManager.api.dtos.requests.loginInfoRequests;

import lombok.Data;

@Data
public class DeleteLoginInfoRequest {
    private String loginInfoId;
    private String username;
    private String password;
}
