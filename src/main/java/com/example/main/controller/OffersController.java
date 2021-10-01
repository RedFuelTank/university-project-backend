package com.example.main.controller;

import com.example.main.dto.OfferDto;
import com.example.main.dto.RequestDto;
import com.example.main.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/offers")
@RestController
public class OffersController {
  private AdvertisementService advertisementService;

  @Autowired
  public OffersController(AdvertisementService advertisementService) {
    this.advertisementService = advertisementService;
  }

  @GetMapping
  public List<OfferDto> get() {
    return advertisementService.getOffers();
  }

  @GetMapping("{id}")
  public OfferDto getById(@PathVariable Long id) {
    return (OfferDto) advertisementService.findById(id);
  }

  @PostMapping()
  public OfferDto saveOffer(@RequestBody OfferDto offerDto) {
    return advertisementService.save(offerDto);
  }
}
