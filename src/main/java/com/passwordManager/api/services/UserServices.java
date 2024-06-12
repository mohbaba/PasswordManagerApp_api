package com.passwordManager.api.services;

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
import com.passwordManager.api.exceptions.InvalidURLException;

public interface UserServices {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    long countUsers();

    LoginUserResponse login(LoginRequest loginRequest);

    LogoutUserResponse logout(LogoutUserRequest logoutUserRequest);

    boolean isLoggedIn(String username);

    long countLoginInfoFor(String username);

    LoginInfoResponse addLoginInfo(LoginInfoRequest loginInfoRequest) throws InvalidURLException;

    DeleteLoginInfoResponse deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest);

    LoginInfoResponse editLoginInfo(EditLoginInfoRequest editLoginInfoRequest) throws InvalidURLException;
    GetLoginInfoResponse getLoginInfo(GetLoginInfoRequest getLoginInfoRequest);

    IdentityInfoResponse addIdentityInfo(IdentityInfoRequest identityInfoRequest);

    long countIdentityInfoFor(String username);

    DeleteIdentityInfoResponse deleteIdentityInfo(DeleteIdentityInfoRequest deleteIdentityInfoRequest);

    GetIdentityInfoResponse editIdentityInfo(EditIdentityInfoRequest editIdentityInfoRequest);

    CreditCardInfoResponse addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest);

    int countCreditCardInfoFor(String username);

    DeleteCreditCardInfoResponse deleteCreditCardInfo(DeleteCardInfoRequest deleteCardInfoRequest);

    GetIdentityInfoResponse getIdentityInfo(GetIdentityInfoRequest getIdentityInfoRequest);

    GetCreditCardInfoResponse getCreditCardInfo(GetCardInfoRequest getCardInfoRequest);

    GetCreditCardInfoResponse editCreditCardInfo(EditCardInfoRequest editCardInfoRequest);
}
