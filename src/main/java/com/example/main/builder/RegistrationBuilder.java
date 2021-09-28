package com.example.main.builder;

import com.example.main.builder.exceptions.LackOfInformation;
import com.example.main.dto.RegistrationDto;

public class RegistrationBuilder extends ClientBuilder {
  private String password;

  public RegistrationBuilder(Long id, String username, String name, String surname, String password) {
    super(id, username, name, surname);
    this.password = password;
  }

  public RegistrationDto apply() throws LackOfInformation {
    if (!hasMandatoryInformation()) {
      throw new LackOfInformation();
    }
    return new RegistrationDto(this.id, this.username, this.password, this.email, this.name, this.surname, this.phoneNumber);
  }

  @Override
  protected boolean hasMandatoryInformation() {
    return super.hasMandatoryInformation() && passwordNotNone();
  }

  private boolean passwordNotNone() {
    return this.password != null;
  }
}
