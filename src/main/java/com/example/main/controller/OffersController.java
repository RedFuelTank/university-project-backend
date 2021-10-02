package com.example.main.controller;

import com.example.main.dto.OfferDto;
import com.example.main.dto.RequestDto;
import com.example.main.model.User;
import com.example.main.service.AdvertisementService;
import com.example.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/offers")
@RestController
public class OffersController {
    private final AdvertisementService advertisementService;
    private final UserService userService;


    @Autowired
    public OffersController(AdvertisementService advertisementService, UserService userService) {
        this.advertisementService = advertisementService;
        this.userService = userService;
    }

    @GetMapping
    public List<OfferDto> get() {
        List<OfferDto> offers = advertisementService.getOffers();
        offers.forEach(request -> request.setUser(userService.findById((long) request.getAuthorId())));
        return offers;
    }

    @GetMapping("/{id}")
    public OfferDto getById(@PathVariable Long id) {
        OfferDto offer = (OfferDto) advertisementService.findById(id);
        offer.setUser(userService.findById((long) offer.getAuthorId()));
        return offer;
    }

    @PostMapping()
    public OfferDto saveOffer(@RequestBody OfferDto offerDto) {
        return advertisementService.save(offerDto);
    }
}
