package com.example.main.builder;

import com.example.main.builder.exceptions.LackOfInformation;
import com.example.main.dto.UserDto;

public class UserBuilder extends ClientBuilder {
  public UserBuilder(Long id, String username, String name, String surname) {
    super(id, username, name, surname);
  }

  public UserDto apply() throws LackOfInformation {
    if (!hasMandatoryInformation()) {
      throw new LackOfInformation();
    }
    
    return new UserDto(this.id, this.username, this.email, this.name, this.surname, this.phoneNumber);
  }


}
