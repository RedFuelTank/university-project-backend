package com.example.main.builder;

import com.example.main.dto.UserDto;

public class UserDtoBuilder extends ClientBuilder {
  protected Long id;

  public UserDtoBuilder(Long id, String username, String name, String surname) {
    super(username, name, surname);
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  private boolean idNotNone() {
    return id != null;
  }

  public UserDto apply() {
    return new UserDto(this.id, this.username, this.email, this.name, this.surname, this.phoneNumber);
  }
}
