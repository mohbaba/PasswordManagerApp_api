package com.passwordManager.api.dtos.requests.creditCardInfoRequests;

import lombok.Data;

@Data
public class DeleteCardInfoRequest {
    private String username;
    private String cardId;

}
