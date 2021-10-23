package com.example.main.controller;

import com.example.main.dto.RequestDto;
import com.example.main.factory.AdvertisementFactory;
import com.example.main.model.Advertisement;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RequestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void delete() {

    }

    @Test
    void getRequestListTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/requests"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<RequestDto> RequestDtos = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });
        assertEquals(4, RequestDtos.size());
    }

    @Test
    void testCanAddNewRequest() throws Exception {
        Advertisement request = new Advertisement("Complete my project", "please",
                1, 0, 0, "", "25-11-2023", Advertisement.Type.REQUEST);
        request.setId(6L);
        RequestDto requestDto = AdvertisementFactory.createRequestDto(request);
        String requestDtoJson = objectMapper.writeValueAsString(requestDto);

        MvcResult postResult = mockMvc.perform(MockMvcRequestBuilders.post("/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        String resultRequestDtoJson = postResult.getResponse().getContentAsString();

        assertEquals(requestDtoJson.substring(7), resultRequestDtoJson.substring(7)); //Id is ignored because it varies.

        MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders.get("/requests"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = getResult.getResponse().getContentAsString();
        List<RequestDto> users = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });
        assertEquals(4, users.size());
    }
}
