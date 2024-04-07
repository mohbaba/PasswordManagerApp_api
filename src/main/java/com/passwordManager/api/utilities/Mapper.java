package com.passwordManager.api.utilities;

import com.passwordManager.api.data.models.*;
import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.CreditCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.EditCardInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.EditIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.IdentityInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.LoginInfoRequest;
import com.passwordManager.api.dtos.responses.*;
import com.passwordManager.api.dtos.responses.creditCardResponses.CreditCardInfoResponse;
import com.passwordManager.api.dtos.responses.creditCardResponses.GetCreditCardInfoResponse;
import com.passwordManager.api.dtos.responses.identityInfoResponses.DeleteIdentityInfoResponse;
import com.passwordManager.api.dtos.responses.identityInfoResponses.GetIdentityInfoResponse;
import com.passwordManager.api.dtos.responses.identityInfoResponses.IdentityInfoResponse;
import com.passwordManager.api.dtos.responses.loginInfoResponses.GetLoginInfoResponse;
import com.passwordManager.api.dtos.responses.loginInfoResponses.LoginInfoResponse;

import static com.passwordManager.api.utilities.Cipher.decrypt;
import static com.passwordManager.api.utilities.Cipher.encrypt;
import static com.passwordManager.api.utilities.CreditCardValidator.getCardType;
import static com.passwordManager.api.utilities.Utils.getExpirationMonth;
import static com.passwordManager.api.utilities.Utils.getExpirationYear;

public class Mapper {



    public static User map(RegisterUserRequest registerUserRequest){
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(encrypt(registerUserRequest.getPassword()));
        return user;
    }

    public static RegisterUserResponse map(User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUsername(user.getUsername());
        registerUserResponse.setPassword(decrypt(user.getPassword()));
        return registerUserResponse;
    }

    public static LoginUserResponse mapTo(User user){
        LoginUserResponse loginUserResponse = new LoginUserResponse();
        loginUserResponse.setUserId(user.getId());
        loginUserResponse.setUsername(user.getUsername());
        return loginUserResponse;
    }

    public static LogoutUserResponse mapFrom(User user){
        LogoutUserResponse logoutUserResponse = new LogoutUserResponse();
        logoutUserResponse.setUsername(user.getUsername());
        logoutUserResponse.setLogoutSuccessful(true);
        return logoutUserResponse;
    }

    public static LoginInfoResponse map(Login login){
        LoginInfoResponse loginInfoResponse = new LoginInfoResponse();
        loginInfoResponse.setId(login.getId());
        loginInfoResponse.setSavedUsername(login.getSavedUsername());
        loginInfoResponse.setSuccessfullyAdded(true);
//        loginInfoResponse.setSavedPassword(decrypt(login.getSavedPassword()));
        loginInfoResponse.setSavedWebsite(login.getWebsite());
        return loginInfoResponse;
    }

    public static GetLoginInfoResponse mapToResponse(Login login, GetLoginInfoResponse getLoginInfoResponse){
        getLoginInfoResponse.setSavedUsername(login.getSavedUsername());
        getLoginInfoResponse.setSavedWebsite(login.getWebsite());
        return getLoginInfoResponse;

    }

    public static IdentityInfoResponse mapResponse(Identity savedIdentity, User user) {
        IdentityInfoResponse identityInfoResponse = new IdentityInfoResponse();
        identityInfoResponse.setId(savedIdentity.getId());
        identityInfoResponse.setFirstName(savedIdentity.getFirstName());
        identityInfoResponse.setMiddleName(savedIdentity.getMiddleName());
        identityInfoResponse.setLastName(savedIdentity.getLastName());
        identityInfoResponse.setUsername(user.getUsername());
        identityInfoResponse.setSuccessfullyAddedNiN(true);
        return identityInfoResponse;
    }

    public static GetIdentityInfoResponse mapResponse(Identity savedIdentity) {
        GetIdentityInfoResponse response = new GetIdentityInfoResponse();
        response.setAddress(savedIdentity.getAddress());
        response.setId(savedIdentity.getId());
        response.setFirstName(savedIdentity.getFirstName());
        response.setMiddleName(savedIdentity.getMiddleName());
        response.setLastName(savedIdentity.getLastName());
        return response;
    }

    public static CreditCardInfoResponse mapResponse(CreditCardInfo creditCard, User user) {
        CreditCardInfoResponse creditCardInfoResponse = new CreditCardInfoResponse();
        creditCardInfoResponse.setCardId(creditCard.getId());
        creditCardInfoResponse.setCardholderName(creditCard.getCardholderName());
        creditCardInfoResponse.setUsername(user.getUsername());
        creditCardInfoResponse.setExpirationMonth(creditCard.getExpiryMonth());
        creditCardInfoResponse.setExpirationYear(creditCard.getExpiryYear());
        creditCardInfoResponse.setCardType(creditCard.getCardType());
        return creditCardInfoResponse;
    }

    public static Login map(LoginInfoRequest loginInfoRequest) {
        Login login = new Login();
        login.setSavedUsername(loginInfoRequest.getUsernameToBeSaved());
//        login.setSavedPassword(encrypt(loginInfoRequest.getPasswordToBeSaved()));
        login.setWebsite(loginInfoRequest.getWebsite());
        return login;
    }
    public static Identity map(IdentityInfoRequest identityInfoRequest) {
        Identity identity = new Identity();
        identity.setFirstName(identityInfoRequest.getFirstName());
        identity.setMiddleName(identityInfoRequest.getMiddleName());
        identity.setLastName(identityInfoRequest.getLastName());
        identity.setAddress(identityInfoRequest.getAddress());
        return identity;
    }

    public static Identity map(EditIdentityInfoRequest editIdentityInfoRequest, Identity foundIdentity) {
        if (editIdentityInfoRequest.getNationalIdentityNumber() != null)foundIdentity.setNationalIdentityNumber(editIdentityInfoRequest.getNationalIdentityNumber());
        if (editIdentityInfoRequest.getFirstName() != null)foundIdentity.setFirstName(editIdentityInfoRequest.getFirstName());
        if (editIdentityInfoRequest.getLastName() != null)foundIdentity.setLastName(editIdentityInfoRequest.getLastName());
        if (editIdentityInfoRequest.getMiddleName() != null)foundIdentity.setMiddleName(editIdentityInfoRequest.getMiddleName());
        if (editIdentityInfoRequest.getAddress() != null)foundIdentity.setAddress(editIdentityInfoRequest.getAddress());
        return foundIdentity;
    }

    public static DeleteIdentityInfoResponse mapResponse() {
        DeleteIdentityInfoResponse response = new DeleteIdentityInfoResponse();
        response.setDeleted(true);
        return response;
    }

    public static CreditCardInfo map(CreditCardInfoRequest creditCardInfoRequest) {
        CreditCardInfo creditCardInfo = new CreditCardInfo();
        creditCardInfo.setCardholderName(creditCardInfoRequest.getCardholderName());
        creditCardInfo.setCardType(getCardType(creditCardInfoRequest.getCardNumber()));
        creditCardInfo.setExpiryMonth(getExpirationMonth(creditCardInfoRequest.getExpirationMonth()));
        creditCardInfo.setExpiryYear(getExpirationYear(creditCardInfoRequest.getExpirationYear()));
        return creditCardInfo;
    }



    public static GetCreditCardInfoResponse map(CreditCardInfo savedCard) {
        GetCreditCardInfoResponse response = new GetCreditCardInfoResponse();
        response.setId(savedCard.getId());
        response.setCardholderName(savedCard.getCardholderName());
        response.setCardType(savedCard.getCardType());
        response.setExpirationMonth(getExpirationMonth(savedCard.getExpiryMonth()));
        response.setExpirationYear(getExpirationYear(savedCard.getExpiryYear()));
        return response;
    }

    public static CreditCardInfo map(EditCardInfoRequest editCardInfoRequest, CreditCardInfo foundedCreditCardInfo) {
        if (editCardInfoRequest.getCardholderName()!= null) foundedCreditCardInfo.setCardholderName(editCardInfoRequest.getCardholderName());
        if (editCardInfoRequest.getExpirationMonth()!= 0) foundedCreditCardInfo.setExpiryMonth(getExpirationMonth(editCardInfoRequest.getExpirationMonth()));
        if (editCardInfoRequest.getExpirationYear()!= 0) foundedCreditCardInfo.setExpiryYear(getExpirationYear(editCardInfoRequest.getExpirationYear()));
        return foundedCreditCardInfo;
    }
}
