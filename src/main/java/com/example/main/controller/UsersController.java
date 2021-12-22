package com.example.main.controller;

import com.example.main.config.security.UserUtil;
import com.example.main.config.security.jwt.JwtTokenProvider;
import com.example.main.dto.RegistrationDto;
import com.example.main.dto.UserDto;
import com.example.main.model.User;
import com.example.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/authorizationTest")
    private Object authorizationTest() {
        return UserUtil.getLoggedInUser();
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

}
