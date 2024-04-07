package com.passwordManager.api.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document("Card Details")
public class CreditCardInfo {
    private String id;
    private String cardholderName;
    private String cardNumber;
    private String cvv;
    private CreditCardType cardType;
    private int expiryMonth;
    private int expiryYear;
}
