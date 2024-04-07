package com.passwordManager.api.services;

import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.dtos.responses.*;

public interface UserServices {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    long countUsers();

    LoginUserResponse login(LoginRequest loginRequest);

    LogoutUserResponse logout(LogoutUserRequest logoutUserRequest);

    boolean isLoggedIn(String username);

    long countLoginInfoFor(String username);

    LoginInfoResponse addLoginInfo(LoginInfoRequest loginInfoRequest);

    DeleteLoginInfoResponse deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest);

    LoginInfoResponse editLoginInfo(EditLoginInfoRequest editLoginInfoRequest);
    GetLoginInfoResponse getLoginInfo(GetLoginInfoRequest getLoginInfoRequest);

    IdentityInfoResponse addIdentityInfo(IdentityInfoRequest identityInfoRequest);

    long countIdentityInfoFor(String username);

    DeleteIdentityInfoResponse deleteIdentityInfo(DeleteIdentityInfoRequest deleteIdentityInfoRequest);

    IdentityInfoResponse editIdentityInfo(EditIdentityInfoRequest editIdentityInfoRequest);

    CreditCardInfoResponse addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest);

    int countCreditCardInfoFor(String username);

    DeleteCreditCardInfoResponse deleteCreditCardInfo(DeleteCardInfoRequest deleteCardInfoRequest);

    GetIdentityInfoResponse getIdentityInfo(GetIdentityInfoRequest getIdentityInfoRequest);

    GetCreditCardInfoResponse getCreditCardInfo(GetCardInfoRequest getCardInfoRequest);

    GetCreditCardInfoResponse editCreditCardInfo(EditGetCardInfoRequest editGetCardInfoRequest);
}
