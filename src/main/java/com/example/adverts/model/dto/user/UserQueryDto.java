package com.example.adverts.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserQueryDto implements Serializable {

    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String role;

}
