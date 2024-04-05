package com.passwordManager.api.data.repositories;


import com.passwordManager.api.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
