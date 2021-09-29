package com.example.main.builder;

import com.example.main.dto.RegistrationDto;
import com.example.main.dto.UserDto;
import com.example.main.model.User;

public class UserFactory {
  public static UserDto createUserDto(User user) {
    return new UserDto(user.getId(), user.getUsername(), user.getEmail().orElse(null),
      user.getName(), user.getSurname(), user.getPhoneNumber().orElse(null));
  }

  public static User createUser(RegistrationDto registrationDto) {
    return new User(registrationDto.getUsername(), registrationDto.getPassword(),
      registrationDto.getEmail().orElse(null), registrationDto.getName(),
      registrationDto.getSurname(), registrationDto.getPhoneNumber().orElse(null));
  }
}
