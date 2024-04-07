package com.passwordManager.api.dtos.responses;

import com.passwordManager.api.data.models.CreditCardType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GetCreditCardInfoResponse {
    private String id;
    private String cardholderName;
    private String cardNumber;
    private String cvv;
    private CreditCardType cardType;
    private LocalDate expirationMonth;
    private LocalDate expirationYear;
}
