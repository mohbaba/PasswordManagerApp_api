package com.passwordManager.api.dtos.requests;

import lombok.Data;

@Data
public class CreditCardInfoRequest {
    private String username;
    private String cardholderName;
    private String cardNumber;
    private String cvv;
    private int expirationMonth;
    private int expirationYear;
}
