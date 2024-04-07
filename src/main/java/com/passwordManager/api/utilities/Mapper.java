package com.passwordManager.api.utilities;

import com.passwordManager.api.data.models.*;
import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.dtos.responses.*;

import java.util.Optional;

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
        creditCardInfoResponse.setExpirationMonth(creditCard.getExpirationMonth());
        creditCardInfoResponse.setExpirationYear(creditCard.getExpirationYear());
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
        identity.setNationalIdentityNumber(identityInfoRequest.getNationalIdentityNumber());
        return identity;
    }

    public static Identity map(EditIdentityInfoRequest editIdentityInfoRequest,Identity foundIdentity) {
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
        creditCardInfo.setExpirationMonth(getExpirationMonth(creditCardInfoRequest.getExpirationMonth()));
        creditCardInfo.setExpirationYear(getExpirationYear(creditCardInfoRequest.getExpirationYear()));
        return creditCardInfo;
    }

    public static EditCreditCardInfoResponse map(CreditCardInfo editedCard) {
        EditCreditCardInfoResponse response = new EditCreditCardInfoResponse();

        response.setCardType(editedCard.getCardType());
        response.setCardholderName(editedCard.getCardholderName());
        response.setExpirationMonth(editedCard.getExpirationMonth());
        response.setExpirationYear(editedCard.getExpirationYear());
        response.setCardId(editedCard.getId());
        return response;
    }

    public static GetCreditCardInfoResponse mapResponseTo(CreditCardInfo savedCard) {
        GetCreditCardInfoResponse response = new GetCreditCardInfoResponse();
        response.setId(savedCard.getId());
        response.setCardholderName(savedCard.getCardholderName());
        response.setExpirationMonth(savedCard.getExpirationMonth());
        response.setExpirationYear(savedCard.getExpirationYear());
        return response;
    }

    public static CreditCardInfo map(EditGetCardInfoRequest editGetCardInfoRequest,
                                     CreditCardInfo foundedCreditCardInfo) {
        if (editGetCardInfoRequest.getCardholderName()!= null) foundedCreditCardInfo.setCardholderName(editGetCardInfoRequest.getCardholderName());
        if (editGetCardInfoRequest.getExpirationMonth()!= 0) foundedCreditCardInfo.setExpirationMonth(getExpirationMonth(editGetCardInfoRequest.getExpirationMonth()));
        if (editGetCardInfoRequest.getExpirationYear()!= 0) foundedCreditCardInfo.setExpirationYear(getExpirationYear(editGetCardInfoRequest.getExpirationYear()));
        return foundedCreditCardInfo;
    }
}
