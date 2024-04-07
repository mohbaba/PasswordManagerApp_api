package com.passwordManager.api.services;

import com.passwordManager.api.data.models.Identity;
import com.passwordManager.api.dtos.requests.identityInfoRequests.DeleteIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.EditIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.GetIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.IdentityInfoRequest;

public interface IdentityInfoServices {

    Identity addIdentityInfo(IdentityInfoRequest identityInfoRequest);

    Identity deleteIdentityInfo(DeleteIdentityInfoRequest deleteIdentityInfoRequest);

    Identity editIdentityInfo(EditIdentityInfoRequest editIdentityInfoRequest);

    Identity getIdentityInfo(GetIdentityInfoRequest getIdentityInfoRequest);
}
