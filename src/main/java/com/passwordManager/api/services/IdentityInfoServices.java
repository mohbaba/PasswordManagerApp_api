package com.passwordManager.api.services;

import com.passwordManager.api.data.models.Identity;
import com.passwordManager.api.dtos.requests.DeleteIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.EditIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.GetIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.IdentityInfoRequest;

public interface IdentityInfoServices {

    Identity addIdentityInfo(IdentityInfoRequest identityInfoRequest);

    Identity deleteIdentityInfo(DeleteIdentityInfoRequest deleteIdentityInfoRequest);

    Identity editIdentityInfo(EditIdentityInfoRequest editIdentityInfoRequest);

    Identity getIdentityInfo(GetIdentityInfoRequest getIdentityInfoRequest);
}
