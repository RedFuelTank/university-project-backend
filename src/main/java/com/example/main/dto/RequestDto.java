package com.example.main.dto;

public class RequestDto extends AdvertisementDto {
    public RequestDto(Long id, String title, String description, Long authorId) {
        super(id, title, description, authorId);
    }
}

