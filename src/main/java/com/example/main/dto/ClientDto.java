package com.example.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ClientDto {
    @NonNull
    private String username;
    private String email;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    private String phoneNumber;

    public Optional<String> getEmail() {
        return Optional.ofNullable(this.email);
    }

    public Optional<String> getPhoneNumber() {
        return Optional.ofNullable(this.phoneNumber);
    }
}
