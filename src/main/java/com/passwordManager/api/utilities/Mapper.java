package com.passwordManager.api.utilities;

import com.passwordManager.api.data.models.Identity;
import com.passwordManager.api.data.models.Login;
import com.passwordManager.api.data.models.User;
import com.passwordManager.api.dtos.requests.EditIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.IdentityInfoRequest;
import com.passwordManager.api.dtos.requests.LoginInfoRequest;
import com.passwordManager.api.dtos.requests.RegisterUserRequest;
import com.passwordManager.api.dtos.responses.RegisterUserResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.passwordManager.api.utilities.Cipher.encrypt;

public class Mapper {



    public static User map(RegisterUserRequest registerUserRequest){
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        return user;
    }

    public static RegisterUserResponse map(User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUsername(user.getUsername());
        registerUserResponse.setPassword(user.getPassword());
        return registerUserResponse;
    }

    public static Login map(LoginInfoRequest loginInfoRequest) {
        Login login = new Login();
        login.setUsernameToSave(loginInfoRequest.getUsernameToSave());
        login.setPasswordToSave(loginInfoRequest.getPassword());
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
}
