package com.passwordManager.api.data.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("Users")
public class User {
    private String id;
    private String username;
    private boolean isLoggedIn;
    private String password;
    private List<LoginInfo> loginInfoDetails = new ArrayList<>();
    private List<IdentityInfo> identities = new ArrayList<>();
    private List<CreditCardInfo> creditCardDetails = new ArrayList<>();
    private int key;

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }
    public User(){
        SecureRandom secureRandom = new SecureRandom();
        this.key = secureRandom.nextInt(1,25);
    }

    public void setKey(int key){

    }
}
