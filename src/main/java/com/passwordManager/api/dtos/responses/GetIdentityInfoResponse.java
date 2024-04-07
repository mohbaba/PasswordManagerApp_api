package com.passwordManager.api.dtos.responses;

import lombok.Data;

@Data
public class GetIdentityInfoResponse {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationalIdentityNumber;
    private String address;
}
