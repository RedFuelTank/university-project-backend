package com.example.main.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public abstract class AdvertisementDto {
    @NonNull
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private int authorId;

    private String authorUsername;
    private String authorEmail;
    private String authorPhoneNumber;
    private String name;

    private double lat;
    private double lng;
    private String address;

    private String startDate;
    private String expirationDate;

    public void updateUserInfo(UserDto user) {
        setName(user.getName() + " " + user.getSurname());
        user.getEmail().ifPresent(this::setAuthorEmail);
        user.getPhoneNumber().ifPresent(this::setAuthorPhoneNumber);
        setAuthorUsername(user.getUsername());
    }
}
