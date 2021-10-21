package com.example.main.dto;

public abstract class AdvertisementDto {
  private Long id;
  private String title;
  private String description;
  private int authorId;

  private String authorUsername;
  private String authorEmail;
  private String authorPhoneNumber;
  private String name;

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

  public String getAuthorUsername() {
    return authorUsername;
  }

  public void setAuthorUsername(String authorUsername) {
    this.authorUsername = authorUsername;
  }

  public String getAuthorEmail() {
    return authorEmail;
  }

  public void setAuthorEmail(String authorEmail) {
    this.authorEmail = authorEmail;
  }

  public String getAuthorPhoneNumber() {
    return authorPhoneNumber;
  }

  public void setAuthorPhoneNumber(String authorPhoneNumber) {
    this.authorPhoneNumber = authorPhoneNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void updateUserInfo(UserDto user) {
    setName(user.getName() + " " + user.getSurname());
    user.getEmail().ifPresent(this::setAuthorEmail);
    user.getPhoneNumber().ifPresent(this::setAuthorPhoneNumber);
    setAuthorUsername(user.getUsername());
  }
}
