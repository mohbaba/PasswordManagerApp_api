package com.passwordManager.api.dtos.responses;

import lombok.Data;

@Data
public class LoginUserResponse {
    private String userId;
    private String username;
}
