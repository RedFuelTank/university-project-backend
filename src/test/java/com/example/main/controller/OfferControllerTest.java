package com.example.main.controller;

import com.example.main.Application;
import com.example.main.dto.OfferDto;
import com.example.main.dto.RegistrationDto;
import com.example.main.dto.UserDto;
import com.example.main.factory.AdvertisementFactory;
import com.example.main.factory.UserFactory;
import com.example.main.model.Advertisement;
import com.example.main.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OfferControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "kilril", password = "kilril123")
    void getOfferListTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/offers"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<OfferDto> offerDtos = objectMapper.readValue(contentAsString, new TypeReference<>() {});
        assertEquals(1, offerDtos.size());
    }

//    @Test
//    @WithMockUser(username = "danila", password = "danila123")
//    void testCanAddNewOffer() throws Exception {
//        Advertisement offer = new Advertisement("Will complete project for us", "10^10 euro",
//                1L, 0, 0, "", "25-11-2023", Advertisement.Type.OFFER);
//        offer.setId(20L);
//        String offerDtoJson = objectMapper.writeValueAsString(AdvertisementFactory.createOfferDto(offer));
//        MvcResult postResult = mockMvc.perform(MockMvcRequestBuilders.post("/offers")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(offer)))
//                .andExpect(status().isOk())
//                .andReturn();
//        String resultOfferDtoJson = postResult.getResponse().getContentAsString();
//        assertEquals(offerDtoJson, resultOfferDtoJson);
//
//        MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders.get("/offers"))
//                .andExpect(status().isOk())
//                .andReturn();
//        String contentAsString = getResult.getResponse().getContentAsString();
//        List<OfferDto> users = objectMapper.readValue(contentAsString, new TypeReference<>() {});
//        assertEquals(2, users.size());
//    }

}
