package com.passwordManager.api.dtos.responses.creditCardResponses;

import com.passwordManager.api.data.models.CreditCardType;
import lombok.Data;


@Data
public class CreditCardInfoResponse {
    private String cardId;
    private String username;
    private String cardholderName;
    private CreditCardType cardType;
    private int expirationMonth;
    private int expirationYear;
}
