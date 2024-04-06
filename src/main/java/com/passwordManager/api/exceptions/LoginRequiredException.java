package com.passwordManager.api.exceptions;

public class LoginRequiredException extends PasswordManagerException{
    public LoginRequiredException(String message){
        super(message);
    }
}
