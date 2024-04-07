package com.passwordManager.api.dtos.responses;

import com.passwordManager.api.data.models.CreditCardType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreditCardInfoResponse {
    private String cardId;
    private String username;
    private String cardholderName;
    private CreditCardType cardType;
    private LocalDate expirationMonth;
    private LocalDate expirationYear;
}
