package com.passwordManager.api.services;

import com.passwordManager.api.data.models.Login;
import com.passwordManager.api.dtos.requests.DeleteLoginInfoRequest;
import com.passwordManager.api.dtos.requests.EditLoginInfoRequest;
import com.passwordManager.api.dtos.requests.GetLoginInfoRequest;
import com.passwordManager.api.dtos.requests.LoginInfoRequest;

public interface LoginInfoServices {
    Login addLogin(LoginInfoRequest loginInfoRequest);

    Login deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest);

    Login editLoginInfo(EditLoginInfoRequest editLoginInfoRequest);

    Login getLoginInfo(GetLoginInfoRequest getLoginInfoRequest);
}
