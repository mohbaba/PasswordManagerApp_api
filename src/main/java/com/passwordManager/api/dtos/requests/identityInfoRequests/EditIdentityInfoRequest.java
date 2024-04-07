package com.passwordManager.api.dtos.requests.identityInfoRequests;

import lombok.Data;

@Data
public class EditIdentityInfoRequest {
    private String identityInfoId;
    private String username;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationalIdentityNumber;
    private String address;
}
