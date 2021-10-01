package com.example.main.dto;

public abstract class AdvertisementDto {
  private Long id;
  private String title;
  private String description;
  private int authorId;

  public AdvertisementDto(Long id, String title, String description, int authorId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.authorId = authorId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
