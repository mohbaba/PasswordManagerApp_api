package com.passwordManager.api.dtos.requests;

import com.passwordManager.api.data.models.CreditCardType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EditGetCardInfoRequest {
    private String cardId;
    private String username;
    private String password;
    private String cardholderName;
    private String cardNumber;
    private String cvv;
    private int expirationMonth;
    private int expirationYear;
}
