package com.passwordManager.api.dtos.requests.identityInfoRequests;

import lombok.Data;

@Data
public class GetIdentityInfoRequest {
    private String identityInfoId;
    private String username;
    private String password;
}
