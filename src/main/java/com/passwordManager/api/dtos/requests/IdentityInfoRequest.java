package com.passwordManager.api.dtos.requests;

import lombok.Data;

@Data
public class IdentityInfoRequest {
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationalIdentityNumber;
    private String passportNumber;
    private String address;
}
