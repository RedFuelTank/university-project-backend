package com.example.main.dto;

public class RequestDto {
    private Long id;
    private String title;
    private String description;
    private UserDto author;

    public RequestDto(Long id, String title, String description, UserDto author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }
}
