package com.passwordManager.api.exceptions;

public class IncorrectUsernameFormatException extends PasswordManagerException{
    public IncorrectUsernameFormatException(String message){
        super(message);
    }
}
