package com.passwordManager.api.data.repositories;

import com.passwordManager.api.data.models.Login;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginRepository extends MongoRepository<Login, String> {
    Login deleteLoginById(String id);
}
