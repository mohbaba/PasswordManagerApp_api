package com.passwordManager.api.dtos.responses.creditCardResponses;

import com.passwordManager.api.data.models.CreditCardType;
import lombok.Data;


@Data
public class EditCreditCardInfoResponse {
    private String cardId;
    private String cardholderName;
    private String cardNumber;
    private String cvv;
    private CreditCardType cardType;
    private int expirationMonth;
    private int expirationYear;
}
