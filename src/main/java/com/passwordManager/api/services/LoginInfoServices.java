package com.passwordManager.api.services;

import com.passwordManager.api.data.models.Login;
import com.passwordManager.api.dtos.requests.loginInfoRequests.DeleteLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.EditLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.GetLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.LoginInfoRequest;

public interface LoginInfoServices {
    Login addLogin(LoginInfoRequest loginInfoRequest);

    Login deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest);

    Login editLoginInfo(EditLoginInfoRequest editLoginInfoRequest);

    Login getLoginInfo(GetLoginInfoRequest getLoginInfoRequest);
}
