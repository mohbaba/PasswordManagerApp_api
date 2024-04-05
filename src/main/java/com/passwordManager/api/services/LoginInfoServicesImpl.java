package com.passwordManager.api.services;

import com.passwordManager.api.data.models.Login;
import com.passwordManager.api.data.repositories.LoginRepository;
import com.passwordManager.api.dtos.requests.DeleteLoginInfoRequest;
import com.passwordManager.api.dtos.requests.EditLoginInfoRequest;
import com.passwordManager.api.dtos.requests.LoginInfoRequest;
import com.passwordManager.api.exceptions.IncorrectPasswordException;
import com.passwordManager.api.exceptions.LoginInfoNotFoundException;
import com.passwordManager.api.exceptions.UnauthorizedSaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<Login> login = loginRepository.findById(editLoginInfoRequest.getPostId());
        if (login.isEmpty())
            throw new LoginInfoNotFoundException("Login info entered does not " +
                    "exist");
        Login foundLogin = login.get();
        foundLogin.setUsernameToSave(editLoginInfoRequest.getNewUsername());
        foundLogin.setPasswordToSave(editLoginInfoRequest.getNewPassword());

        return loginRepository.save(foundLogin);
    }

    private void checkPassword(LoginInfoRequest loginInfoRequest){
        if (loginInfoRequest.getPassword().isEmpty())throw new IncorrectPasswordException(
                "password entered not accepted");
        if (loginInfoRequest.getPassword().isBlank())throw new IncorrectPasswordException(
                "password cannot be blank");
    }

    private void checkLogin(Login login){
        if (login == null)throw new UnauthorizedSaveException("Action not permitted");
    }
}
