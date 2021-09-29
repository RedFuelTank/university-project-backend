package com.example.main.controller;

import com.example.main.dto.RegistrationDto;
import com.example.main.dto.UserDto;
import com.example.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UsersController {
  private final UserService userService;

  @Autowired
  public UsersController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<UserDto> getUsers(@RequestParam Optional<String> name) {
    return userService.getAll(name);
  }

  @PostMapping
  public UserDto saveUser(@RequestBody RegistrationDto userDto) {
    return userService.save(userDto);
  }
}
