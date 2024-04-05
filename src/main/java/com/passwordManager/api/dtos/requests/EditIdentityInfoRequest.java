package com.passwordManager.api.dtos.requests;

import lombok.Data;

@Data
public class EditIdentityInfoRequest {
    private String identityInfoId;
    private String user;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationalIdentityNumber;
    private String passportNumber;
    private String address;
}
