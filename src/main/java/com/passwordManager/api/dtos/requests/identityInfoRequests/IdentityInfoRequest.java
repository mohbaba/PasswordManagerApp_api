package com.passwordManager.api.dtos.requests.identityInfoRequests;

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

    public void setNationalIdentityNumber(String nationalIdentityNumber) {
        this.nationalIdentityNumber = nationalIdentityNumber.replace(" ", "");
    }
}
