package com.passwordManager.api.exceptions;

public class IncorrectCardDetailsException extends PasswordManagerException{
    public IncorrectCardDetailsException(String message){
        super(message);
    }
}
