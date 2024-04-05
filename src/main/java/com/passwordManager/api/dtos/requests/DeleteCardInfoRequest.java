package com.passwordManager.api.dtos.requests;

import lombok.Data;

@Data
public class DeleteCardInfoRequest {
    private String user;
    private String cardId;

}
