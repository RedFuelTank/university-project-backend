package com.example.main.dto;

public class UserDto extends ClientDto {
  private Long id;

  public UserDto(Long id, String username, String email, String name, String surname, String phoneNumber) {
    super(username, email, name, surname, phoneNumber);
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
