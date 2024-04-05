package com.passwordManager.api.exceptions;

public class UnauthorizedSaveException extends PasswordManagerException{
    public UnauthorizedSaveException(String message){
        super(message);
    }
}
