package com.passwordManager.api.services;

import com.passwordManager.api.data.models.CreditCardInfo;
import com.passwordManager.api.dtos.requests.CreditCardInfoRequest;

public interface CreditCardInfoServices {
    CreditCardInfo addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest);
}
