package com.example.main.service;

import com.example.main.builder.UserBuilder;
import com.example.main.dto.UserDto;
import com.example.main.model.User;
import com.example.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

    public List<UserDto> getAll(@RequestParam Optional<String> nameOp) {
      if (nameOp.isEmpty()) {
        return convertToDto(userRepository.findAll());
      } else {
        return convertToDto(userRepository.findByNameIsLike('%' + nameOp.get().toLowerCase() + '%'));
      }
    }

    private List<UserDto> convertToDto(List<User> users) {
    return users.stream()
      .map(user -> {
        UserBuilder userBuilder = new UserBuilder(user.getId(), user.getUsername(), user.getName(), user.getSurname());
        user.getEmail().ifPresent(userBuilder::setEmail);
        user.getPhoneNumber().ifPresent(userBuilder::setPhoneNumber);
        return userBuilder.apply();
      })
      .collect(Collectors.toList());

    }
}
