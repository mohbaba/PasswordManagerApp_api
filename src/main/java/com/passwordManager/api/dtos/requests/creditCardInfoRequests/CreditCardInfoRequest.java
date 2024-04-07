package com.passwordManager.api.dtos.requests.creditCardInfoRequests;

import lombok.Data;

@Data
public class CreditCardInfoRequest {
    private String username;
    private String cardholderName;

    private String cardNumber;

    private String cvv;
    private int expirationMonth;
    private int expirationYear;
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber.replaceAll("\\s","");
    }
}
