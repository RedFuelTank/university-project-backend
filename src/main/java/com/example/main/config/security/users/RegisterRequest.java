package com.example.main.config.security.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
}
