package com.passwordManager.api.services;

import com.passwordManager.api.data.models.LoginInfo;
import com.passwordManager.api.dtos.requests.loginInfoRequests.DeleteLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.EditLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.GetLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.LoginInfoRequest;

public interface LoginInfoServices {
    LoginInfo addLogin(LoginInfoRequest loginInfoRequest);

    LoginInfo deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest);

    LoginInfo editLoginInfo(EditLoginInfoRequest editLoginInfoRequest);

    LoginInfo getLoginInfo(GetLoginInfoRequest getLoginInfoRequest);
}
