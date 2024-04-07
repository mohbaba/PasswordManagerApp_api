package com.passwordManager.api.dtos.requests.identityInfoRequests;


import lombok.Data;

@Data
public class DeleteIdentityInfoRequest {
    private String identityInfoId;
    private String username;

}
