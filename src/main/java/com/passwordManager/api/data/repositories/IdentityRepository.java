package com.passwordManager.api.data.repositories;

import com.passwordManager.api.data.models.Identity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IdentityRepository extends MongoRepository<Identity, String> {
    Identity deleteIdentityById(String id);
}
