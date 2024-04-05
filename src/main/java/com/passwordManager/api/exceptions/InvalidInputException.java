package com.passwordManager.api.exceptions;

public class InvalidInputException extends PasswordManagerException{
    public InvalidInputException(String message){
        super(message);
    }
}
