package com.passwordManager.api.services;

import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.dtos.responses.RegisterUserResponse;

public interface UserServices {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    long countUsers();

    void login(LoginRequest loginRequest);

    boolean isLoggedIn(String username);

    void addNewClassifiedInfo(ClassInfoTypeRequest classInfoTypeRequest);

    long countLoginType(String username);

    void addLoginInfo(LoginInfoRequest loginInfoRequest);

    void deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest);

    void editLoginInfo(EditLoginInfoRequest editLoginInfoRequest);

    void addIdentityInfo(IdentityInfoRequest identityInfoRequest);

    long countIdentityInfo(String username);

    void deleteIdentityInfo(DeleteIdentityInfoRequest deleteIdentityInfoRequest);

    void editIdentityInfo(EditIdentityInfoRequest editIdentityInfoRequest);

    void addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest);

    int countCreditCardInfo(String username);

    void deleteCreditCardInfo(DeleteCardInfoRequest deleteCardInfoRequest);
}
