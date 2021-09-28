package com.example.main.dto;

public class UserDto extends ClientDto{
  public UserDto(Long id, String username, String email, String name, String surname, String phoneNumber) {
    super(id, username, email, name, surname, phoneNumber);
  }
}
