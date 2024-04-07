package com.passwordManager.api.services;

import com.passwordManager.api.data.models.CreditCardInfo;
import com.passwordManager.api.data.repositories.CardRepository;
import com.passwordManager.api.dtos.requests.CreditCardInfoRequest;
import com.passwordManager.api.dtos.requests.DeleteCardInfoRequest;
import com.passwordManager.api.dtos.requests.EditGetCardInfoRequest;
import com.passwordManager.api.dtos.requests.GetCardInfoRequest;
import com.passwordManager.api.exceptions.CreditCardInfoNotFoundException;
import com.passwordManager.api.exceptions.FieldRequiredException;
import com.passwordManager.api.exceptions.IncorrectCardDetailsException;
import com.passwordManager.api.exceptions.InvalidCreditCardException;
import com.passwordManager.api.utilities.NumericCipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.passwordManager.api.utilities.CreditCardValidator.*;
import static com.passwordManager.api.utilities.Mapper.map;
import static com.passwordManager.api.utilities.Utils.getExpirationMonth;
import static com.passwordManager.api.utilities.Utils.getExpirationYear;

@Service
public class CreditCardInfoServicesImpl implements CreditCardInfoServices{
    @Autowired
    private CardRepository cardRepository;
    @Override
    public CreditCardInfo addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest) {
        if (creditCardInfoRequest.getCardNumber() == null)throw new FieldRequiredException("Card " +
                "number required");
        validateCard(creditCardInfoRequest.getCardNumber());
        validateCvv(creditCardInfoRequest.getCvv());
        CreditCardInfo creditCardInfo = map(creditCardInfoRequest);
        creditCardInfo.setCvv(NumericCipher.encrypt(Integer.parseInt(creditCardInfoRequest.getCvv())));
        creditCardInfo.setCardNumber(NumericCipher.encrypt(Long.parseLong(creditCardInfoRequest.getCardNumber())));


        return cardRepository.save(creditCardInfo) ;
    }

    @Override
    public CreditCardInfo deleteCreditCardInfo(DeleteCardInfoRequest deleteCardInfoRequest) {
        if (deleteCardInfoRequest.getCardId() == null)throw new CreditCardInfoNotFoundException(String.format("Credit card info with id: %s not found", (Object) null));
        return cardRepository.deleteCreditCardInfoById(deleteCardInfoRequest.getCardId());
    }

    @Override
    public CreditCardInfo getCreditCardInfo(GetCardInfoRequest getCardInfoRequest){
        if (getCardInfoRequest.getCardId() == null)throw new CreditCardInfoNotFoundException(String.format("Credit card info with id: %s not found", (Object) null));
        Optional<CreditCardInfo> creditCardInfo = cardRepository.findById(getCardInfoRequest.getCardId());
        if (creditCardInfo.isEmpty())throw new CreditCardInfoNotFoundException(String.format("Credit card info with id: %s not found", (Object) null));
        return creditCardInfo.get();
    }

    @Override
    public CreditCardInfo editCreditCardInfo(EditGetCardInfoRequest editGetCardInfoRequest) {
        if (editGetCardInfoRequest.getCardId() == null) throw new FieldRequiredException("Card Id" +
                " not specified");
        Optional<CreditCardInfo> creditCardInfo = cardRepository.findById(editGetCardInfoRequest.getCardId());
        if (creditCardInfo.isEmpty())throw new CreditCardInfoNotFoundException(String.format(
                "Credit card info with id: %s not found", editGetCardInfoRequest.getCardId()));
        CreditCardInfo foundedCreditCardInfo = map(editGetCardInfoRequest,creditCardInfo.get());

        encryptSensitiveInfo(editGetCardInfoRequest, foundedCreditCardInfo);


        return cardRepository.save(foundedCreditCardInfo);
    }

    private void encryptSensitiveInfo(EditGetCardInfoRequest editGetCardInfoRequest, CreditCardInfo foundedCreditCardInfo) {
        if (editGetCardInfoRequest.getCardNumber()!= null) {
            validateCard(editGetCardInfoRequest.getCardNumber());
            foundedCreditCardInfo.setCardNumber(NumericCipher.encrypt(Long.parseLong(editGetCardInfoRequest.getCardNumber())));
            foundedCreditCardInfo.setCardType(getCardType(editGetCardInfoRequest.getCardNumber()));
        }
        if (editGetCardInfoRequest.getCvv()!= null) {
            validateCvv(editGetCardInfoRequest.getCvv());
            foundedCreditCardInfo.setCvv(NumericCipher.encrypt(Integer.parseInt(editGetCardInfoRequest.getCvv())));
        }
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
