package com.passwordManager.api.services;

import com.passwordManager.api.data.models.Login;
import com.passwordManager.api.data.repositories.LoginRepository;
import com.passwordManager.api.dtos.requests.DeleteLoginInfoRequest;
import com.passwordManager.api.dtos.requests.EditLoginInfoRequest;
import com.passwordManager.api.dtos.requests.GetLoginInfoRequest;
import com.passwordManager.api.dtos.requests.LoginInfoRequest;
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
    public Login addLogin(LoginInfoRequest loginInfoRequest) {
        checkPassword(loginInfoRequest);
        Login login = map(loginInfoRequest);
        checkLogin(login);
        return loginRepository.save(login);
    }

    @Override
    public Login deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest) {
        return loginRepository.deleteLoginById(deleteLoginInfoRequest.getLoginInfoId());
    }

    @Override
    public Login editLoginInfo(EditLoginInfoRequest editLoginInfoRequest) {
        if (editLoginInfoRequest.getPostId() == null) throw new LoginInfoNotFoundException("Enter" +
                " Id Of Login");
        Optional<Login> login = loginRepository.findById(editLoginInfoRequest.getPostId());
        if (login.isEmpty())
            throw new LoginInfoNotFoundException("Login info entered does not " +
                    "exist");
        Login foundLogin = login.get();
        if (editLoginInfoRequest.getNewPassword() != null)foundLogin.setSavedPassword(encrypt(editLoginInfoRequest.getNewPassword()));
        if (editLoginInfoRequest.getNewUsername() != null)foundLogin.setSavedUsername(editLoginInfoRequest.getNewUsername());



        return loginRepository.save(foundLogin);
    }

    @Override
    public Login getLoginInfo(GetLoginInfoRequest getLoginInfoRequest) {
        if (getLoginInfoRequest.getLoginInfoId() == null)throw new LoginInfoNotFoundException(
                "Enter details to fetch login info");
        Optional<Login> login = loginRepository.findById(getLoginInfoRequest.getLoginInfoId());
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

    private void checkLogin(Login login){
        if (login == null)throw new UnauthorizedSaveException("Action not permitted");
    }
}
