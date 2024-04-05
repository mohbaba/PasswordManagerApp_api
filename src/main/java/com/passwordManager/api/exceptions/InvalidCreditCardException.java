package com.passwordManager.api.exceptions;

public class InvalidCreditCardException extends PasswordManagerException{
    public InvalidCreditCardException(String message){
        super(message);
    }
}
