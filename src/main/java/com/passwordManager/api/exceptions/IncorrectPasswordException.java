package com.passwordManager.api.exceptions;

public class IncorrectPasswordException extends PasswordManagerException{
    public IncorrectPasswordException(String message){
        super(message);
    }
}
