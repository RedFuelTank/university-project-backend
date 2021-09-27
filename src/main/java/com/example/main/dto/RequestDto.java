package com.example.main.dto;

public class RequestDto {
    private Long id;
    private String title;
    private String description;
    private int authorId;

    public RequestDto(Long id, String title, String description, int author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorId = author;
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
