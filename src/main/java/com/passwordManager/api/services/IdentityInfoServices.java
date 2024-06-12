package com.passwordManager.api.services;

import com.passwordManager.api.data.models.IdentityInfo;
import com.passwordManager.api.dtos.UserData;
import com.passwordManager.api.dtos.requests.identityInfoRequests.DeleteIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.EditIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.GetIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.IdentityInfoRequest;

public interface IdentityInfoServices {

    IdentityInfo addIdentityInfo(IdentityInfoRequest identityInfoRequest, UserData userData);

    IdentityInfo deleteIdentityInfo(DeleteIdentityInfoRequest deleteIdentityInfoRequest);

    IdentityInfo editIdentityInfo(EditIdentityInfoRequest editIdentityInfoRequest);

    IdentityInfo getIdentityInfo(GetIdentityInfoRequest getIdentityInfoRequest);
}
