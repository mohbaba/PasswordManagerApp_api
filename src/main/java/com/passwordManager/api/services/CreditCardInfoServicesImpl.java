package com.passwordManager.api.services;

import com.passwordManager.api.data.models.CreditCardInfo;
import com.passwordManager.api.dtos.requests.CreditCardInfoRequest;
import com.passwordManager.api.exceptions.IncorrectCardDetailsException;
import org.springframework.stereotype.Service;

import static com.passwordManager.api.utilities.CreditCardValidator.lengthChecker;
import static com.passwordManager.api.utilities.Mapper.map;

@Service
public class CreditCardInfoServicesImpl implements CreditCardInfoServices{
    @Override
    public CreditCardInfo addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest) {
        validateCvv(creditCardInfoRequest.getCvv());
        CreditCardInfo creditCardInfo = map(creditCardInfoRequest);


        return creditCardInfo;
    }

    private void validateCvv(String cvv){
        if (cvv.length() != 3)throw new IncorrectCardDetailsException(String.format("The CVV " +
                "number(%s) you entered is incorrect ",cvv));
        for (int index = 0; index < cvv.length(); index++) {
            if (Character.isLetter(cvv.charAt(index)))throw new IncorrectCardDetailsException(String.format("Incorrect input format: %s ",cvv.charAt(index)));
        }
    }

    private void validateCardNumber(String cardNumber){
        if (!lengthChecker())throw new IncorrectCardDetailsException(String.format("Invalid card " +
                "number"));
        for (int index = 0; index < cardNumber.length(); index++) {
            if (Character.isLetter(cardNumber.charAt(index)))throw new IncorrectCardDetailsException(String.format("Incorrect input format: %s ",cardNumber.charAt(index)));
        }
    }


}
