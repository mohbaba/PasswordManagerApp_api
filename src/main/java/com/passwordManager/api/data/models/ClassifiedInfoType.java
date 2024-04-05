package com.passwordManager.api.data.models;


import com.passwordManager.api.exceptions.InvalidInputException;
import lombok.Getter;

@Getter
public enum ClassifiedInfoType {
    LOGIN("login"),
    IDENTITY("identity"),
    CARD("card");

    private final String message;

    ClassifiedInfoType(String message){
        this.message = message;
    }

    public static ClassifiedInfoType getType(String message) {
        for (ClassifiedInfoType type : ClassifiedInfoType.values()) {
            if (type.getMessage().equals(message)) {
                return type;
            }
        }
        throw new InvalidInputException("Incorrect type entered, try again");
    }


}
