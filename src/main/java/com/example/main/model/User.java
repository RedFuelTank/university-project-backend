package com.example.main.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "users_table")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    private String email;
    private String phoneNumber;

    public User(String username, String password, String email, String name, String surname, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(this.email);
    }

    public Optional<String> getPhoneNumber() {
        return Optional.ofNullable(this.phoneNumber);
    }
}

