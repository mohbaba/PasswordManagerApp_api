package com.passwordManager.api.exceptions;

public class UserNotFoundException extends PasswordManagerException{
    public UserNotFoundException(String message){
        super(message);
    }
}
