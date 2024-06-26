package com.passwordManager.api.data.repositories;

import com.passwordManager.api.data.models.CreditCardInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardRepository extends MongoRepository<CreditCardInfo, String> {
    CreditCardInfo deleteCreditCardInfoById(String id);
}
