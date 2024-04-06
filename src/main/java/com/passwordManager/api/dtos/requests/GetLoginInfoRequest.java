package com.passwordManager.api.dtos.requests;

import lombok.Data;

@Data
public class GetLoginInfoRequest {
    private String loginInfoId;
    private String username;
    private String password;

}
