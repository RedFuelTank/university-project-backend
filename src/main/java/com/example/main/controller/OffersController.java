package com.example.main.controller;

import com.example.main.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/offers")
@RestController
public class OffersController {
    private AdvertisementService advertisementService;

    @Autowired
    public OffersController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping
    public String get() {
        return "Hello, World!";
    }
}
