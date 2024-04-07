package com.passwordManager.api.dtos.requests;

import lombok.Data;

@Data
public class GetCardInfoRequest {
    private String cardId;
    private String username;
    private String password;
}
