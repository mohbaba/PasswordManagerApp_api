package com.passwordManager.api.dtos.requests;

import lombok.Data;

@Data
public class GetIdentityInfoRequest {
    private String identityInfoId;
    private String username;
    private String password;
}
