package com.passwordManager.api.data.repositories;

import com.passwordManager.api.data.models.LoginInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginRepository extends MongoRepository<LoginInfo, String> {
    LoginInfo deleteLoginById(String id);
}
