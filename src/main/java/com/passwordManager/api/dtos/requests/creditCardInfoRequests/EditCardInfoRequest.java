package com.passwordManager.api.dtos.requests.creditCardInfoRequests;

import lombok.Data;

@Data
public class EditCardInfoRequest {
    private String cardId;
    private String username;
    private String password;
    private String cardholderName;
    private String cardNumber;
    private String cvv;
    private int expirationMonth;
    private int expirationYear;

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber.replaceAll("\\s", "");
    }

}
