package com.passwordManager.api.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("Users")
public class User {
    private String id;
    private String username;
    private boolean isLoggedIn;
    private String password;
    private List<Login> loginDetails = new ArrayList<>();
    private List<Identity> identities = new ArrayList<>();
    private List<CreditCardInfo> creditCardDetails = new ArrayList<>();

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }
}
