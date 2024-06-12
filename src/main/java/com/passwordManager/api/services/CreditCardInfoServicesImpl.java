package com.passwordManager.api.services;

import com.passwordManager.api.data.models.CreditCardInfo;
import com.passwordManager.api.data.repositories.CardRepository;
import com.passwordManager.api.dtos.UserData;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.CreditCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.DeleteCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.EditCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.GetCardInfoRequest;
import com.passwordManager.api.exceptions.CreditCardInfoNotFoundException;
import com.passwordManager.api.exceptions.FieldRequiredException;
import com.passwordManager.api.exceptions.IncorrectCardDetailsException;
import com.passwordManager.api.exceptions.InvalidCreditCardException;
import org.cipher.NumericCipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.passwordManager.api.utilities.CreditCardValidator.*;
import static com.passwordManager.api.utilities.Mapper.map;

@Service
public class CreditCardInfoServicesImpl implements CreditCardInfoServices{
    @Autowired
    private CardRepository cardRepository;
    @Override
    public CreditCardInfo addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest,
                                            UserData user) {
        if (creditCardInfoRequest.getCardNumber() == null)throw new FieldRequiredException("Card " +
                "number required");
        validateCard(creditCardInfoRequest.getCardNumber().replaceAll("\\s ", ""));
        validateCvv(creditCardInfoRequest.getCvv().replaceAll("\\s", ""));
        CreditCardInfo creditCardInfo = map(creditCardInfoRequest);
        creditCardInfo.setCvv(NumericCipher.encrypt(Integer.parseInt(creditCardInfoRequest.getCvv()), user.getUserData().getKey()));
        creditCardInfo.setCardNumber(NumericCipher.encrypt(Long.parseLong(creditCardInfoRequest.getCardNumber()), user.getUserData().getKey()));


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
    public CreditCardInfo editCreditCardInfo(EditCardInfoRequest editCardInfoRequest,
                                             UserData userData) {
        if (editCardInfoRequest.getCardId() == null) throw new FieldRequiredException("Card Id" +
                " not specified");
        Optional<CreditCardInfo> creditCardInfo = cardRepository.findById(editCardInfoRequest.getCardId());
        if (creditCardInfo.isEmpty())throw new CreditCardInfoNotFoundException(String.format(
                "Credit card info with id: %s not found", editCardInfoRequest.getCardId()));
        CreditCardInfo foundedCreditCardInfo = map(editCardInfoRequest,creditCardInfo.get());
        encryptSensitiveInfo(editCardInfoRequest, foundedCreditCardInfo, userData);
        return cardRepository.save(foundedCreditCardInfo);
    }

    private void encryptSensitiveInfo(EditCardInfoRequest editCardInfoRequest, CreditCardInfo foundedCreditCardInfo, UserData userData) {
        if (editCardInfoRequest.getCardNumber() != null){
            validateCard(editCardInfoRequest.getCardNumber());
            foundedCreditCardInfo.setCardNumber(NumericCipher.encrypt(Long.parseLong(editCardInfoRequest.getCardNumber()), userData.getUserData().getKey()));
            foundedCreditCardInfo.setCardType(getCardType(editCardInfoRequest.getCardNumber()));
        }
        if (editCardInfoRequest.getCvv()!= null){
            validateCvv(editCardInfoRequest.getCvv());
            foundedCreditCardInfo.setCvv(NumericCipher.encrypt(Integer.parseInt(editCardInfoRequest.getCvv()), userData.getUserData().getKey()));
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
