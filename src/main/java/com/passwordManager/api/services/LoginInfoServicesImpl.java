package com.passwordManager.api.services;

import com.passwordManager.api.data.models.LoginInfo;
import com.passwordManager.api.data.repositories.LoginRepository;
import com.passwordManager.api.dtos.requests.loginInfoRequests.DeleteLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.EditLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.GetLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.LoginInfoRequest;
import com.passwordManager.api.exceptions.IncorrectPasswordException;
import com.passwordManager.api.exceptions.LoginInfoNotFoundException;
import com.passwordManager.api.exceptions.UnauthorizedSaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.passwordManager.api.utilities.Cipher.encrypt;
import static com.passwordManager.api.utilities.Mapper.map;

@Service
public class LoginInfoServicesImpl implements LoginInfoServices{
    @Autowired
    private LoginRepository loginRepository;
    @Override
    public LoginInfo addLogin(LoginInfoRequest loginInfoRequest) {
        checkPassword(loginInfoRequest);
        LoginInfo loginInfo = map(loginInfoRequest);
        loginInfo.setSavedPassword(encrypt(loginInfoRequest.getPasswordToBeSaved()));
        checkLogin(loginInfo);
        return loginRepository.save(loginInfo);
    }

    @Override
    public LoginInfo deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest) {
        return loginRepository.deleteLoginById(deleteLoginInfoRequest.getLoginInfoId());
    }

    @Override
    public LoginInfo editLoginInfo(EditLoginInfoRequest editLoginInfoRequest) {
        if (editLoginInfoRequest.getPostId() == null) throw new LoginInfoNotFoundException("Enter" +
                " Id Of Login");
        Optional<LoginInfo> login = loginRepository.findById(editLoginInfoRequest.getPostId());
        if (login.isEmpty())
            throw new LoginInfoNotFoundException("Login info entered does not " +
                    "exist");
        LoginInfo foundLoginInfo = login.get();
        if (editLoginInfoRequest.getNewPassword() != null) foundLoginInfo.setSavedPassword(encrypt(editLoginInfoRequest.getNewPassword()));
        if (editLoginInfoRequest.getNewUsername() != null) foundLoginInfo.setSavedUsername(editLoginInfoRequest.getNewUsername());



        return loginRepository.save(foundLoginInfo);
    }

    @Override
    public LoginInfo getLoginInfo(GetLoginInfoRequest getLoginInfoRequest) {
        if (getLoginInfoRequest.getLoginInfoId() == null)throw new LoginInfoNotFoundException(
                "Enter details to fetch login info");
        Optional<LoginInfo> login = loginRepository.findById(getLoginInfoRequest.getLoginInfoId());
        if (login.isEmpty())
            throw new LoginInfoNotFoundException("Login info entered does not " +
                    "exist");
        return login.get();

    }

    private void checkPassword(LoginInfoRequest loginInfoRequest){
        if (loginInfoRequest.getPasswordToBeSaved().isEmpty())throw new IncorrectPasswordException(
                "password entered not accepted");
        if (loginInfoRequest.getPasswordToBeSaved().isBlank())throw new IncorrectPasswordException(
                "password cannot be blank");
    }

    private void checkLogin(LoginInfo loginInfo){
        if (loginInfo == null)throw new UnauthorizedSaveException("Action not permitted");
    }
}
