package com.passwordManager.api.utilities;

import com.passwordManager.api.data.models.*;
import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.dtos.responses.LoginUserResponse;
import com.passwordManager.api.dtos.responses.RegisterUserResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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

    public static Login map(LoginInfoRequest loginInfoRequest) {
        Login login = new Login();
        login.setUsernameToSave(loginInfoRequest.getUsernameToSave());
        login.setPasswordToSave(encrypt(loginInfoRequest.getPassword()));
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
        identity.setPassportNumber(identityInfoRequest.getPassportNumber());
        return identity;
    }

    public static Identity map(EditIdentityInfoRequest editIdentityInfoRequest,Optional<Identity> identity) {
        Identity foundIdentity = identity.get();
        foundIdentity.setPassportNumber(editIdentityInfoRequest.getPassportNumber());
        foundIdentity.setNationalIdentityNumber(editIdentityInfoRequest.getNationalIdentityNumber());
        foundIdentity.setFirstName(editIdentityInfoRequest.getFirstName());
        foundIdentity.setLastName(editIdentityInfoRequest.getLastName());
        foundIdentity.setMiddleName(editIdentityInfoRequest.getMiddleName());
        foundIdentity.setAddress(editIdentityInfoRequest.getAddress());
        return foundIdentity;
    }

    public static CreditCardInfo map(CreditCardInfoRequest creditCardInfoRequest) {
        CreditCardInfo creditCardInfo = new CreditCardInfo();
        creditCardInfo.setCardholderName(creditCardInfoRequest.getCardholderName());
        creditCardInfo.setCardNumber(encrypt(creditCardInfoRequest.getCardNumber()));
        creditCardInfo.setCvv(encrypt(creditCardInfoRequest.getCvv()));
        creditCardInfo.setCardType(getCardType(creditCardInfoRequest.getCardNumber()));
        creditCardInfo.setExpirationMonth(getExpirationMonth(creditCardInfoRequest.getExpirationMonth()));
        creditCardInfo.setExpirationYear(getExpirationYear(creditCardInfoRequest.getExpirationYear()));
        return creditCardInfo;
    }
}
