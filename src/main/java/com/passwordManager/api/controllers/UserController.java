package com.passwordManager.api.controllers;


import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.CreditCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.DeleteCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.EditCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.GetCardInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.DeleteIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.EditIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.GetIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.IdentityInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.DeleteLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.EditLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.GetLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.LoginInfoRequest;
import com.passwordManager.api.dtos.responses.*;
import com.passwordManager.api.dtos.responses.creditCardResponses.CreditCardInfoResponse;
import com.passwordManager.api.dtos.responses.creditCardResponses.DeleteCreditCardInfoResponse;
import com.passwordManager.api.dtos.responses.creditCardResponses.GetCreditCardInfoResponse;
import com.passwordManager.api.dtos.responses.identityInfoResponses.DeleteIdentityInfoResponse;
import com.passwordManager.api.dtos.responses.identityInfoResponses.GetIdentityInfoResponse;
import com.passwordManager.api.dtos.responses.identityInfoResponses.IdentityInfoResponse;
import com.passwordManager.api.dtos.responses.loginInfoResponses.DeleteLoginInfoResponse;
import com.passwordManager.api.dtos.responses.loginInfoResponses.GetLoginInfoResponse;
import com.passwordManager.api.dtos.responses.loginInfoResponses.LoginInfoResponse;
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

    @PatchMapping("/edit_login_information")
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

    @PostMapping("/add_identity_information")
    public ResponseEntity<?> addIdentityInfo(@RequestBody IdentityInfoRequest identityInfoRequest){
        try {
            IdentityInfoResponse response = userServices.addIdentityInfo(identityInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete_identity_information")
    public ResponseEntity<?> deleteIdentityInfo(@RequestBody DeleteIdentityInfoRequest deleteIdentityInfoRequest){
        try {
            DeleteIdentityInfoResponse response = userServices.deleteIdentityInfo(deleteIdentityInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/edit_identity_information")
    public ResponseEntity<?> getIdentityInfo(@RequestBody EditIdentityInfoRequest editIdentityInfoRequest){
        try {
            GetIdentityInfoResponse response =
                    userServices.editIdentityInfo(editIdentityInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/get_identity_information")
    public ResponseEntity<?> editIdentityInfo(@RequestBody GetIdentityInfoRequest getIdentityInfoRequest){
        try {
            GetIdentityInfoResponse response = userServices.getIdentityInfo(getIdentityInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/add_credit_card_information")
    public ResponseEntity<?> addCreditCardInfo(@RequestBody CreditCardInfoRequest creditCardInfoRequest){
        try {
            CreditCardInfoResponse response = userServices.addCreditCardInfo(creditCardInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete_credit_card_information")
    public ResponseEntity<?> deleteCreditCardInfo(@RequestBody DeleteCardInfoRequest deleteCardInfoRequest){
        try {
            DeleteCreditCardInfoResponse response =
                    userServices.deleteCreditCardInfo(deleteCardInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/get_credit_card_information")
    public ResponseEntity<?> getCreditCardInfo(@RequestBody GetCardInfoRequest getCardInfoRequest){
        try {
            GetCreditCardInfoResponse response = userServices.getCreditCardInfo(getCardInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/edit_credit_card_information")
    public ResponseEntity<?> editCreditCardInfo(@RequestBody EditCardInfoRequest editCardInfoRequest){
        try {
            GetCreditCardInfoResponse response =
                    userServices.editCreditCardInfo(editCardInfoRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/count_number_of_saved_identity_info_for/{username}")
    public ResponseEntity<?> countIdentityInfoFor(@PathVariable("username") String username){
        try {
            long response = userServices.countIdentityInfoFor(username);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/count_number_of_saved_login_info_for/{username}")
    public ResponseEntity<?> countLoginInfoFor(@PathVariable("username") String username){
        try {
            long response = userServices.countLoginInfoFor(username);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/count_number_of_saved_credit_card_info_for/{username}")
    public ResponseEntity<?> countCreditCardInfoFor(@PathVariable("username") String username){
        try {
            long response = userServices.countCreditCardInfoFor(username);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (PasswordManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }
}
