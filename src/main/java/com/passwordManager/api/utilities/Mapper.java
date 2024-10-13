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
        user.setPassword(encrypt(registerUserRequest.getPassword(),user.getKey()));
        return user;
    }

    public static RegisterUserResponse map(User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUsername(user.getUsername());
        registerUserResponse.setPassword(decrypt(user.getPassword(), user.getKey()));
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

    public static LoginInfoResponse map(LoginInfo loginInfo){
        LoginInfoResponse loginInfoResponse = new LoginInfoResponse();
        loginInfoResponse.setId(loginInfo.getId());
        loginInfoResponse.setSavedUsername(loginInfo.getSavedUsername());
        loginInfoResponse.setSuccessfullyAdded(true);
//        loginInfoResponse.setSavedPassword(decrypt(login.getSavedPassword()));
        loginInfoResponse.setSavedWebsite(loginInfo.getWebsite());
        return loginInfoResponse;
    }

    public static GetLoginInfoResponse mapToResponse(LoginInfo loginInfo, GetLoginInfoResponse getLoginInfoResponse){
        getLoginInfoResponse.setSavedUsername(loginInfo.getSavedUsername());
        getLoginInfoResponse.setSavedWebsite(loginInfo.getWebsite());
        return getLoginInfoResponse;

    }

    public static IdentityInfoResponse mapResponse(IdentityInfo savedIdentityInfo, User user) {
        IdentityInfoResponse identityInfoResponse = new IdentityInfoResponse();
        identityInfoResponse.setId(savedIdentityInfo.getId());
        identityInfoResponse.setFirstName(savedIdentityInfo.getFirstName());
        identityInfoResponse.setMiddleName(savedIdentityInfo.getMiddleName());
        identityInfoResponse.setLastName(savedIdentityInfo.getLastName());
        identityInfoResponse.setUsername(user.getUsername());
        identityInfoResponse.setSuccessfullyAddedNiN(true);
        return identityInfoResponse;
    }

    public static GetIdentityInfoResponse mapResponse(IdentityInfo savedIdentityInfo) {
        GetIdentityInfoResponse response = new GetIdentityInfoResponse();
        response.setAddress(savedIdentityInfo.getAddress());
        response.setId(savedIdentityInfo.getId());
        response.setFirstName(savedIdentityInfo.getFirstName());
        response.setMiddleName(savedIdentityInfo.getMiddleName());
        response.setLastName(savedIdentityInfo.getLastName());
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

    public static LoginInfo map(LoginInfoRequest loginInfoRequest) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setSavedUsername(loginInfoRequest.getUsernameToBeSaved());
//        login.setSavedPassword(encrypt(loginInfoRequest.getPasswordToBeSaved()));
        loginInfo.setWebsite(loginInfoRequest.getWebsite());
        return loginInfo;
    }
    public static IdentityInfo map(IdentityInfoRequest identityInfoRequest) {
        IdentityInfo identityInfo = new IdentityInfo();
        identityInfo.setFirstName(identityInfoRequest.getFirstName());
        identityInfo.setMiddleName(identityInfoRequest.getMiddleName());
        identityInfo.setLastName(identityInfoRequest.getLastName());
        identityInfo.setAddress(identityInfoRequest.getAddress());
        return identityInfo;
    }

    public static IdentityInfo map(EditIdentityInfoRequest editIdentityInfoRequest, IdentityInfo foundIdentityInfo) {
        if (editIdentityInfoRequest.getNationalIdentityNumber() != null) foundIdentityInfo.setNationalIdentityNumber(editIdentityInfoRequest.getNationalIdentityNumber());
        if (editIdentityInfoRequest.getFirstName() != null) foundIdentityInfo.setFirstName(editIdentityInfoRequest.getFirstName());
        if (editIdentityInfoRequest.getLastName() != null) foundIdentityInfo.setLastName(editIdentityInfoRequest.getLastName());
        if (editIdentityInfoRequest.getMiddleName() != null) foundIdentityInfo.setMiddleName(editIdentityInfoRequest.getMiddleName());
        if (editIdentityInfoRequest.getAddress() != null) foundIdentityInfo.setAddress(editIdentityInfoRequest.getAddress());
        return foundIdentityInfo;
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
