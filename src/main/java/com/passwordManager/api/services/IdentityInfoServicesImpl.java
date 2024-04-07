package com.passwordManager.api.services;


import com.passwordManager.api.data.models.Identity;
import com.passwordManager.api.data.repositories.IdentityRepository;
import com.passwordManager.api.dtos.requests.identityInfoRequests.DeleteIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.EditIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.GetIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.IdentityInfoRequest;
import com.passwordManager.api.exceptions.*;
import com.passwordManager.api.utilities.NumericCipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.passwordManager.api.utilities.Mapper.map;
import static com.passwordManager.api.utilities.NumericCipher.encrypt;

@Service
public class IdentityInfoServicesImpl implements IdentityInfoServices{
    @Autowired
    private IdentityRepository identityRepository;
    @Override
    public Identity addIdentityInfo(IdentityInfoRequest identityInfoRequest) {
        validate(identityInfoRequest.getNationalIdentityNumber().replace(" ", ""));
        if (identityInfoRequest.getNationalIdentityNumber().length() != 11)throw new InvalidNiNException("NIN number must contain 11 digits");
        Identity identity = map(identityInfoRequest);
        if (identityInfoRequest.getNationalIdentityNumber() != null)identity.setNationalIdentityNumber(NumericCipher.encrypt(Long.parseLong(identityInfoRequest.getNationalIdentityNumber())));
        return identityRepository.save(identity);
    }

    @Override
    public Identity deleteIdentityInfo(DeleteIdentityInfoRequest deleteIdentityInfoRequest) {
        if (deleteIdentityInfoRequest.getIdentityInfoId() == null) throw new PasswordManagerException("Identity does not exist");
        return identityRepository.deleteIdentityById(deleteIdentityInfoRequest.getIdentityInfoId());
    }

    @Override
    public Identity editIdentityInfo(EditIdentityInfoRequest editIdentityInfoRequest) {
        if (editIdentityInfoRequest.getNationalIdentityNumber() != null)validate(editIdentityInfoRequest.getNationalIdentityNumber());
        if (editIdentityInfoRequest.getIdentityInfoId() == null)throw new FieldRequiredException(
                "Identity information id not specified");
        Optional<Identity> identity = identityRepository.findById(editIdentityInfoRequest.getIdentityInfoId());
        if (identity.isEmpty())throw new IdentityInfoNotFoundException("Identity does not exist");
        Identity foundIdentity = map(editIdentityInfoRequest, identity.get());
        return identityRepository.save(foundIdentity);
    }

    @Override
    public Identity getIdentityInfo(GetIdentityInfoRequest getIdentityInfoRequest) {
        if (getIdentityInfoRequest.getIdentityInfoId() == null)throw new FieldRequiredException(
                "Identity information id not specified");
        Optional<Identity> identity = identityRepository.findById(getIdentityInfoRequest.getIdentityInfoId());
        if (identity.isEmpty())throw new IdentityInfoNotFoundException("Identity does not exist");
        return identity.get();
    }


    private void validate(String ninNumber){
        for (int position = 0; position < ninNumber.length(); position++) {
            char character = ninNumber.charAt(position);
            if (Character.isLetter(character))throw new IncorrectNINnumberException("The NIN " +
                    "number you entered is not valid");
        }

    }


}
