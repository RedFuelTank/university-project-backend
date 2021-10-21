package com.example.main.factory;

import com.example.main.builder.UserBuilder;
import com.example.main.builder.UserDtoBuilder;
import com.example.main.dto.RegistrationDto;
import com.example.main.dto.UserDto;
import com.example.main.model.User;

public class UserFactory {
  public static UserDto createUserDto(User user) {
    UserDtoBuilder userDtoBuilder = new UserDtoBuilder(user.getId(), user.getUsername(),
      user.getName(), user.getSurname());

    user.getPhoneNumber().ifPresent(userDtoBuilder::setPhoneNumber);
    user.getEmail().ifPresent(userDtoBuilder::setEmail);

    return userDtoBuilder.apply();
  }

  public static User createUser(RegistrationDto registrationDto) {
    UserBuilder userBuilder = new UserBuilder(registrationDto.getUsername(), registrationDto.getName(),
      registrationDto.getSurname(), registrationDto.getPassword());

    registrationDto.getPhoneNumber().ifPresent(userBuilder::setPhoneNumber);
    registrationDto.getEmail().ifPresent(userBuilder::setPhoneNumber);

    return userBuilder.apply();
  }
}
