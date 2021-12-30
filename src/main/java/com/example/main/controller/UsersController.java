package com.example.main.controller;

import com.example.main.config.security.UserUtil;
import com.example.main.config.security.users.LoginRequest;
import com.example.main.config.security.users.LoginResponse;
import com.example.main.config.security.users.LoginService;
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

    @Autowired
    private LoginService loginService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam Optional<String> name) {
        return userService.getAll(name);
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody RegistrationDto registrationDto) {
        return userService.register(registrationDto);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
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
