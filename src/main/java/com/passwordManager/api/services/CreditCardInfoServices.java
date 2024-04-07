package com.passwordManager.api.services;

import com.passwordManager.api.data.models.CreditCardInfo;
import com.passwordManager.api.dtos.requests.CreditCardInfoRequest;
import com.passwordManager.api.dtos.requests.DeleteCardInfoRequest;
import com.passwordManager.api.dtos.requests.EditGetCardInfoRequest;
import com.passwordManager.api.dtos.requests.GetCardInfoRequest;

public interface CreditCardInfoServices {
    CreditCardInfo addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest);

    CreditCardInfo deleteCreditCardInfo(DeleteCardInfoRequest deleteCardInfoRequest);

    CreditCardInfo getCreditCardInfo(GetCardInfoRequest getCardInfoRequest);

    CreditCardInfo editCreditCardInfo(EditGetCardInfoRequest editGetCardInfoRequest);
}
