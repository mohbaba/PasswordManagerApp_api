package com.passwordManager.api.services;

import com.passwordManager.api.data.models.CreditCardInfo;
import com.passwordManager.api.dtos.UserData;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.CreditCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.DeleteCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.EditCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.GetCardInfoRequest;

public interface CreditCardInfoServices {
    CreditCardInfo addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest, UserData userData);

    CreditCardInfo deleteCreditCardInfo(DeleteCardInfoRequest deleteCardInfoRequest);

    CreditCardInfo getCreditCardInfo(GetCardInfoRequest getCardInfoRequest);

    CreditCardInfo editCreditCardInfo(EditCardInfoRequest editCardInfoRequest,
                                      UserData userData);
}
