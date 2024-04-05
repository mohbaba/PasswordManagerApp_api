package com.passwordManager.api.dtos.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreditCardInfoRequest {
    private String user;
    private String cardholderName;
    private String cardNumber;
    private String cvv;
    private int expirationMonth;
    private int expirationYear;
}
