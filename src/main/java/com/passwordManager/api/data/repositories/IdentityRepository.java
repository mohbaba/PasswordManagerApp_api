package com.passwordManager.api.data.repositories;

import com.passwordManager.api.data.models.IdentityInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IdentityRepository extends MongoRepository<IdentityInfo, String> {
    IdentityInfo deleteIdentityById(String id);
}
