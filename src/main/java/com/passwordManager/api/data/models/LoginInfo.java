package com.passwordManager.api.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("User Login Details")
public class LoginInfo {
    @Id
    private String id;
    private String savedUsername;
    private String savedPassword;
    private String website;
}
