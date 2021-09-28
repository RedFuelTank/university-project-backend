package com.example.main.controller;

import com.example.main.dto.RequestDto;
import com.example.main.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/requests")
@RestController
public class RequestsController {
  private AdvertisementService advertisementService;

  @Autowired
  public RequestsController(AdvertisementService advertisementService) {
    this.advertisementService = advertisementService;
  }

  @GetMapping
  public List<RequestDto> get() {
    return advertisementService.getRequests();
  }

}
