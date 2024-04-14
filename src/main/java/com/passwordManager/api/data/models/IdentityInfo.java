package com.passwordManager.api.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Identities")
public class IdentityInfo {
    @Id
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationalIdentityNumber;
    private String address;
}
