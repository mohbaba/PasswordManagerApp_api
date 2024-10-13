package com.passwordManager.api.services;

import com.passwordManager.api.data.models.LoginInfo;
import com.passwordManager.api.data.repositories.LoginRepository;
import com.passwordManager.api.dtos.UserData;
import com.passwordManager.api.dtos.requests.loginInfoRequests.DeleteLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.EditLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.GetLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.LoginInfoRequest;
import com.passwordManager.api.exceptions.*;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import static com.passwordManager.api.utilities.Cipher.encrypt;
import static com.passwordManager.api.utilities.Mapper.map;


@Service
public class LoginInfoServicesImpl implements LoginInfoServices{
    @Autowired
    private LoginRepository loginRepository;
    @Override
    public LoginInfo addLogin(LoginInfoRequest loginInfoRequest, UserData userData){
        checkNullFields(loginInfoRequest);
        checkWebsiteURL(loginInfoRequest);
        LoginInfo loginInfo = map(loginInfoRequest);
        loginInfo.setSavedPassword(encrypt(loginInfoRequest.getPasswordToBeSaved(),
                userData.getUserData().getKey()));
        return loginRepository.save(loginInfo);
    }

    private void checkWebsiteURL(LoginInfoRequest loginInfoRequest){
        if (!isValidURL(loginInfoRequest.getWebsite()))throw new InvalidURLException(String.format("The url:%s you entered is not valid", loginInfoRequest.getWebsite()));
    }

    @Override
    public LoginInfo deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest) {
        return loginRepository.deleteLoginById(deleteLoginInfoRequest.getLoginInfoId());
    }

    @Override
    public LoginInfo editLoginInfo(EditLoginInfoRequest editLoginInfoRequest, UserData userData){
        if (editLoginInfoRequest.getPostId() == null) throw new LoginInfoNotFoundException("Enter" +
                " Id Of Login");
        checkWebsiteURL(editLoginInfoRequest);
        Optional<LoginInfo> login = loginRepository.findById(editLoginInfoRequest.getPostId());
        if (login.isEmpty())
            throw new LoginInfoNotFoundException("Login info entered does not " +
                    "exist");
        LoginInfo foundLoginInfo = login.get();
        if (editLoginInfoRequest.getNewUsername() != null) foundLoginInfo.setSavedUsername(editLoginInfoRequest.getNewUsername());
        if (editLoginInfoRequest.getNewPassword() != null) foundLoginInfo.setSavedPassword(encrypt(editLoginInfoRequest.getNewPassword(), userData.getUserData().getKey()));



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

    private void checkNullFields(LoginInfoRequest loginInfoRequest){
        if (loginInfoRequest.getUsernameToBeSaved() == null)throw new FieldRequiredException("Enter username To be saved");
        if (loginInfoRequest.getWebsite() == null)throw new FieldRequiredException("Enter the website url");
        checkPasswordField(loginInfoRequest);
    }

    private void checkWebsiteURL(EditLoginInfoRequest editLoginInfoRequest){
        if (editLoginInfoRequest.getNewWebsite() != null){
            if (!isValidURL(editLoginInfoRequest.getNewWebsite()))throw new InvalidURLException(String.format(
                    "The url:%s you entered is not valid", editLoginInfoRequest.getNewWebsite()));
        }
    }
    private boolean isValidURL(String urlInput){
        try {
//            new URL(urlInput).toURI();
            URL url = new URL(urlInput);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            int responseCode = connect.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return false;
        }

    }

    private void checkPasswordField(LoginInfoRequest loginInfoRequest){
        if (loginInfoRequest.getPasswordToBeSaved().isEmpty())throw new IncorrectPasswordException(
                "password entered not accepted");
        if (loginInfoRequest.getPasswordToBeSaved().isBlank())throw new IncorrectPasswordException(
                "password cannot be blank");
    }

    private void checkLogin(LoginInfo loginInfo){
        if (loginInfo == null)throw new UnauthorizedSaveException("Action not permitted");
    }
}
