package com.passwordManager.api.exceptions;

public class UsernameExistsException extends PasswordManagerException{
    public UsernameExistsException(String message){
        super(message);
    }
}
