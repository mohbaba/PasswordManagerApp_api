package com.passwordManager.api.data.models;


import com.passwordManager.api.exceptions.InvalidInputException;
import lombok.Getter;

@Getter
public enum CreditCardType {
    VISA_CARD("Visa Card"),
    MASTERCARD("Mastercard"),
    AMERICAN_EXPRESS("American Express Card"),
    DISCOVER("Discover");

    private final String message;

    CreditCardType(String message){
        this.message = message;
    }

    public static CreditCardType getType(String message) {
        for (CreditCardType type : CreditCardType.values()) {
            if (type.getMessage().equals(message)) {
                return type;
            }
        }
        throw new InvalidInputException("Incorrect type entered, try again");
    }


}
