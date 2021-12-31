package com.example.main.controller;

import com.example.main.dto.OfferDto;
import com.example.main.service.AdvertisementService;
import com.example.main.service.UserService;
import com.example.main.service.exception.AdvertisementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping({"/offers"})
@RestController
public class OffersPublicController {
    private final AdvertisementService advertisementService;
    private final UserService userService;


    @Autowired
    public OffersPublicController(AdvertisementService advertisementService, UserService userService) {
        this.advertisementService = advertisementService;
        this.userService = userService;
    }

    @GetMapping()
    public List<OfferDto> get(@RequestParam Optional<Integer> page,
                              @RequestParam(required = false) Optional<String> startDate,
                              @RequestParam(required = false) Optional<String> expireDate) {
        List<OfferDto> offers = advertisementService.getOffers(page, startDate, expireDate);
        offers.forEach(request -> request.updateUserInfo(userService.findById((long) request.getAuthorId())));
        return offers;
    }
}
