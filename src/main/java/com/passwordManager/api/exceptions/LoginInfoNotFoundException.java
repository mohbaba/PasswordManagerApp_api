package com.passwordManager.api.exceptions;

public class LoginInfoNotFoundException extends PasswordManagerException{
    public LoginInfoNotFoundException(String message){
        super(message);
    }
}
