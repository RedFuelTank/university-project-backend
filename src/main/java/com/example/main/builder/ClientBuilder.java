package com.example.main.builder;

public abstract class ClientBuilder {
  protected Long id;
  protected String username;
  protected String email;
  protected String name;
  protected String surname;
  protected String phoneNumber;

  public ClientBuilder(Long id, String username, String name, String surname) {
    this.id = id;
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
    return nameNotNone() && usernameNotNone() && idNotNone();
  }

  private boolean idNotNone() {
    return id != null;
  }

  private boolean usernameNotNone() {
    return username != null;
  }

  private boolean nameNotNone() {
    return name != null && surname != null;
  }
}
