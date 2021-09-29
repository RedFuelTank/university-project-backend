package com.example.main.dto;

public class RegistrationDto extends ClientDto {
  private String password;

  public RegistrationDto(String username, String password, String email, String name, String surname, String phoneNumber) {
    super(username, email, name, surname, phoneNumber);
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
