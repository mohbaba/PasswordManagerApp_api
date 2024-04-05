package com.passwordManager.api.services;

import com.passwordManager.api.data.models.CreditCardInfo;
import com.passwordManager.api.data.repositories.CardRepository;
import com.passwordManager.api.dtos.requests.CreditCardInfoRequest;
import com.passwordManager.api.dtos.requests.DeleteCardInfoRequest;
import com.passwordManager.api.exceptions.CreditCardInfoNotFoundException;
import com.passwordManager.api.exceptions.IncorrectCardDetailsException;
import com.passwordManager.api.exceptions.InvalidCreditCardException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.passwordManager.api.utilities.CreditCardValidator.*;
import static com.passwordManager.api.utilities.Mapper.map;

@Service
public class CreditCardInfoServicesImpl implements CreditCardInfoServices{
    @Autowired
    private CardRepository cardRepository;
    @Override
    public CreditCardInfo addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest) {
        validateCard(creditCardInfoRequest.getCardNumber());
        validateCvv(creditCardInfoRequest.getCvv());
        CreditCardInfo creditCardInfo = map(creditCardInfoRequest);


        return cardRepository.save(creditCardInfo) ;
    }

    @Override
    public CreditCardInfo deleteCreditCardInfo(DeleteCardInfoRequest deleteCardInfoRequest) {
        if (deleteCardInfoRequest.getCardId() == null)throw new CreditCardInfoNotFoundException(String.format("Credit card info with id: %s not found", (Object) null));
        return cardRepository.deleteCreditCardInfoById(deleteCardInfoRequest.getCardId());
    }

    private void validateCvv(String cvv){
        if (cvv.length() != 3)throw new IncorrectCardDetailsException(String.format("The CVV " +
                "number(%s) you entered is incorrect ",cvv));
        for (int index = 0; index < cvv.length(); index++) {
            if (Character.isLetter(cvv.charAt(index)))throw new IncorrectCardDetailsException(String.format("Incorrect input format: %s ",cvv.charAt(index)));
        }
    }

    private void validateCard(String cardNumber){
        if (cardNumber == null)throw new InvalidCreditCardException(String.format("Input card " +
                "number"));
        if (!lengthChecker(cardNumber))throw new IncorrectCardDetailsException(String.format("Invalid card" +
                " " +
                "number"));
        if (!validityCheck(cardNumber))throw new InvalidCreditCardException(String.format("Card " +
                "is invalid"));
    }


}
