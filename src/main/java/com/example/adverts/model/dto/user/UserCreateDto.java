package com.example.adverts.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDto implements Serializable {

    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String role;

    public UserCreateDto(String firstname, String lastname, String email, String username, String password, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
