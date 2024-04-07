package com.passwordManager.api.dtos.responses;

import lombok.Data;

@Data
public class IdentityInfoResponse {
    private String id;
    private boolean successfullyAddedNiN;
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;

}
