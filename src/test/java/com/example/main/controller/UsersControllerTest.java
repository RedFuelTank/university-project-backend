package com.example.main.controller;

import com.example.main.dto.RegistrationDto;
import com.example.main.dto.UserDto;
import com.example.main.factory.UserFactory;
import com.example.main.model.User;
import com.example.main.service.exception.BadNameException;
import com.example.main.service.exception.BadSurnameException;
import com.example.main.service.exception.BadUsernameException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;



  @Test
  void getUsersListOfUsersTest() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
      .andExpect(status().isOk())
      .andReturn();
    String contentAsString = result.getResponse().getContentAsString();
    List<UserDto> user = objectMapper.readValue(contentAsString, new TypeReference<>() {});
    assertEquals(2, user.size());
  }

  @Test
  void testCanAddNewUser() throws Exception {
    RegistrationDto newUser = new RegistrationDto("Owl", "qwerty", "owl@goodmail.com",
            "Ed", "Grey", "45679078");
    User user = UserFactory.createUser(newUser);
    user.setId(3L); // new user after being added to database should have ID as 3, as it is the third user in the DB
    String userDtoJson = objectMapper.writeValueAsString(UserFactory.createUserDto(user));
    MvcResult postResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newUser)))
            .andExpect(status().isOk())
            .andReturn();
    String resultUserDtoJson = postResult.getResponse().getContentAsString();
    assertEquals(userDtoJson, resultUserDtoJson);

    MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(status().isOk())
            .andReturn();
    String contentAsString = getResult.getResponse().getContentAsString();
    List<UserDto> users = objectMapper.readValue(contentAsString, new TypeReference<>() {});
    assertEquals(3, users.size());
  }

  @Test
  void testCanAddMultipleUsers() throws Exception {
    List<RegistrationDto> newUsers = new ArrayList<>();
    newUsers.add(new RegistrationDto("Dead", "qwerty", "dead@goodmail.com",
            "Eddy", "Grey", "45679078"));
    newUsers.add(new RegistrationDto("Extra", "qwerty", "extra@goodmail.com",
            "Edd", "Grey", "45679078"));
    newUsers.add(new RegistrationDto("Super", "qwerty", "super@goodmail.com",
            "Edmond", "Grey", "45679078"));

    for (RegistrationDto user : newUsers) {
      mockMvc.perform(MockMvcRequestBuilders.post("/users")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(user)))
              .andExpect(status().isOk());
    }

    MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(status().isOk())
            .andReturn();
    String contentAsString = getResult.getResponse().getContentAsString();
    List<UserDto> users = objectMapper.readValue(contentAsString, new TypeReference<>() {});
    assertEquals(6, users.size()); // as we already had two users with previous test, we add 3 more and get 5
  }

  @Test
  void testAddingNewUserGivesErrorIfUsernameBlank() {
    RegistrationDto newUser = new RegistrationDto("", "qwerty", "owl@goodmail.com",
            "Hondo", "Grey", "45679078");
    Exception exception = assertThrows(NestedServletException.class, () ->
            mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newUser)))
            .andExpect(status().isInternalServerError()));
    assertEquals(exception.getCause().getClass(), BadUsernameException.class);
  }

  @Test
  void testAddingNewUserGivesErrorIfNameBlank() {
    RegistrationDto newUser = new RegistrationDto("Mine", "qwerty", "owl@goodmail.com",
            "", "Grey", "45679078");
    Exception exception = assertThrows(NestedServletException.class, () ->
            mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newUser)))
            .andExpect(status().isInternalServerError()));
    assertEquals(exception.getCause().getClass(), BadNameException.class);
  }

  @Test
  void testAddingNewUserGivesErrorIfSurnameBlank() {
    RegistrationDto newUser = new RegistrationDto("Waaat", "qwerty", "owl@goodmail.com",
            "Mondo", "", "45679078");
    Exception exception = assertThrows(NestedServletException.class, () ->
            mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newUser)))
            .andExpect(status().isInternalServerError()));
    assertEquals(exception.getCause().getClass(), BadSurnameException.class);
  }
}
