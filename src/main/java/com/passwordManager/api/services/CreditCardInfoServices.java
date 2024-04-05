package com.passwordManager.api.services;

import com.passwordManager.api.data.models.CreditCardInfo;
import com.passwordManager.api.dtos.requests.CreditCardInfoRequest;
import com.passwordManager.api.dtos.requests.DeleteCardInfoRequest;

public interface CreditCardInfoServices {
    CreditCardInfo addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest);

    CreditCardInfo deleteCreditCardInfo(DeleteCardInfoRequest deleteCardInfoRequest);
}
