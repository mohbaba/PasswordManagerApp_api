package com.passwordManager.api.dtos.requests.loginInfoRequests;

import lombok.Data;

@Data
public class EditLoginInfoRequest {
    private String postId;
    private String newUsername;
    private String newPassword;
    private String username;
    private String newWebsite;
}
