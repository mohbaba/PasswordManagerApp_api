package com.passwordManager.api.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("User Login Details")
public class Login {
    @Id
    private String id;
    private String usernameToSave;
    private String passwordToSave;
    private String website;
}
