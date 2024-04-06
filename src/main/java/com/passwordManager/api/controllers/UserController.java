package com.passwordManager.api.controllers;


import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.dtos.responses.*;
import com.passwordManager.api.exceptions.PasswordManagerException;
import com.passwordManager.api.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutUserRequest logoutUserRequest){
        try {
            LogoutUserResponse response = userServices.logout(logoutUserRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/add_login_information")
    public ResponseEntity<?> addLoginInfo(@RequestBody LoginInfoRequest loginInfoRequest){
        try {
            LoginInfoResponse response = userServices.addLoginInfo(loginInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/edit_login_information")
    public ResponseEntity<?> addLoginInfo(@RequestBody EditLoginInfoRequest editLoginInfoRequest){
        try {
            LoginInfoResponse response = userServices.editLoginInfo(editLoginInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/get_login_information")
    public ResponseEntity<?> getLoginInfo(@RequestBody GetLoginInfoRequest getLoginInfoRequest){
        try {
            GetLoginInfoResponse response = userServices.getLoginInfo(getLoginInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete_login_information")
    public ResponseEntity<?> deleteLoginInfo(@RequestBody DeleteLoginInfoRequest deleteLoginInfoRequest){
        try {
            DeleteLoginInfoResponse response = userServices.deleteLoginInfo(deleteLoginInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

}
