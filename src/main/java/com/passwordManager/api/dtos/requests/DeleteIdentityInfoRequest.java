package com.passwordManager.api.dtos.requests;


import lombok.Data;

@Data
public class DeleteIdentityInfoRequest {
    private String identityInfoId;
    private String user;

}
