package com.passwordManager.api.dtos.requests;

import lombok.Data;

@Data
public class DeleteLoginInfoRequest {
    private String loginInfoId;
    private String username;
}
