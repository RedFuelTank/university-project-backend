package com.example.main.dto;

import java.util.Optional;

public abstract class ClientDto {
  private String username;
  private String email;
  private String name;
  private String surname;
  private String phoneNumber;

  public ClientDto(String username, String email, String name, String surname, String phoneNumber) {
    this.username = username;
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.phoneNumber = phoneNumber;
  }

  public String getUsername() {
    return username;
  }

  public Optional<String> getEmail() {
    return Optional.ofNullable(this.email);
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public Optional<String> getPhoneNumber() {
    return Optional.ofNullable(this.phoneNumber);
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
