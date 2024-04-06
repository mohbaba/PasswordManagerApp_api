package com.passwordManager.api.controllers;


import com.passwordManager.api.dtos.requests.LoginRequest;
import com.passwordManager.api.dtos.requests.RegisterUserRequest;
import com.passwordManager.api.dtos.responses.ApiResponse;
import com.passwordManager.api.dtos.responses.LoginUserResponse;
import com.passwordManager.api.dtos.responses.RegisterUserResponse;
import com.passwordManager.api.exceptions.PasswordManagerException;
import com.passwordManager.api.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class UserController {
    @Autowired
    UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest registerUserRequest){
        try {
            RegisterUserResponse response = userServices.register(registerUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            LoginUserResponse response = userServices.login(loginRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }


}
