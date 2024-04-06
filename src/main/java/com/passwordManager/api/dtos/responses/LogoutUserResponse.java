package com.passwordManager.api.dtos.responses;

import lombok.Data;

@Data
public class LogoutUserResponse {
    private String username;
    private boolean isLogoutSuccessful;
}
