package com.passwordManager.api.exceptions;

public class FieldRequiredException extends PasswordManagerException{
    public FieldRequiredException(String message){
        super(message);
    }
}
