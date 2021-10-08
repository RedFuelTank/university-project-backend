package com.example.main.builder;

import com.example.main.dto.UserDto;
import com.example.main.model.User;

public abstract class ClientBuilder {
  protected String username;
  protected String email;
  protected String name;
  protected String surname;
  protected String phoneNumber;

  public ClientBuilder(String username, String name, String surname) {
    this.username = username;
    this.name = name;
    this.surname = surname;
  }

  public ClientBuilder setEmail(String email) {
    this.email = email;
    return this;
  }

  public ClientBuilder setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  protected boolean hasMandatoryInformation() {
    return nameNotNone() && usernameNotNone();
  }

  private boolean usernameNotNone() {
    return username != null;
  }

  private boolean nameNotNone() {
    return name != null && surname != null;
  }
}
