package com.passwordManager.api.data.repositories;

import com.passwordManager.api.data.models.CardDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardRepository extends MongoRepository<CardDetails, String> {
}
