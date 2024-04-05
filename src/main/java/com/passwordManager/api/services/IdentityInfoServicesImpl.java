package com.passwordManager.api.services;


import com.passwordManager.api.data.models.Identity;
import com.passwordManager.api.data.repositories.IdentityRepository;
import com.passwordManager.api.dtos.requests.DeleteIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.EditIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.IdentityInfoRequest;
import com.passwordManager.api.exceptions.IdentityInfoNotFoundException;
import com.passwordManager.api.exceptions.IncorrectNINnumberException;
import com.passwordManager.api.exceptions.InvalidNiNException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.passwordManager.api.utilities.Mapper.map;

@Service
public class IdentityInfoServicesImpl implements IdentityInfoServices{
    @Autowired
    private IdentityRepository identityRepository;
    @Override
    public Identity addIdentityInfo(IdentityInfoRequest identityInfoRequest) {
        validate(identityInfoRequest.getNationalIdentityNumber());
        if (identityInfoRequest.getNationalIdentityNumber().length() != 11)throw new InvalidNiNException("Must contain 11 digits");
        Identity identity = map(identityInfoRequest);

        return identityRepository.save(identity);
    }

    @Override
    public Identity deleteIdentityInfo(DeleteIdentityInfoRequest deleteIdentityInfoRequest) {

        return identityRepository.deleteIdentityById(deleteIdentityInfoRequest.getIdentityInfoId());
    }

    @Override
    public Identity editIdentityInfo(EditIdentityInfoRequest editIdentityInfoRequest) {
        validate(editIdentityInfoRequest.getNationalIdentityNumber());
        Optional<Identity> identity = identityRepository.findById(editIdentityInfoRequest.getIdentityInfoId());
        if (identity.isEmpty())throw new IdentityInfoNotFoundException("Identity does not exist");
        Identity foundIdentity = map(editIdentityInfoRequest, identity);
        return identityRepository.save(foundIdentity);
    }



    private void validate(String NinNumber){
        for (int position = 0; position < NinNumber.length(); position++) {
            char character = NinNumber.charAt(position);
            if (Character.isLetter(character))throw new IncorrectNINnumberException("The NIN " +
                    "number you entered is not valid");
        }

    }


}
