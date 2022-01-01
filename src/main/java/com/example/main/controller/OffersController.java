package com.example.main.controller;

import com.example.main.config.security.ApplicationRoles;
import com.example.main.dto.OfferDto;
import com.example.main.dto.RequestDto;
import com.example.main.service.AdvertisementService;
import com.example.main.service.UserService;
import com.example.main.service.exception.AdvertisementNotFoundException;
import com.example.main.service.exception.PageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.main.config.security.ApplicationRoles.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Secured(USER)
@RequestMapping({"/offers"})
@RestController
public class OffersController {
    private final AdvertisementService advertisementService;
    private final UserService userService;


    @Autowired
    public OffersController(AdvertisementService advertisementService, UserService userService) {
        this.advertisementService = advertisementService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public OfferDto getById(@PathVariable Long id) {
        try {
            OfferDto offer = (OfferDto) advertisementService.findById(id);
            offer.updateUserInfo(userService.findById((long) offer.getAuthorId()));
            return offer;
        } catch (ClassCastException e) {
            throw new AdvertisementNotFoundException();
        }
    }

    @GetMapping("/authorId/{authorId}")
    public List<OfferDto> getByAuthorId(@PathVariable Long authorId) {
        return advertisementService.getOffersByAuthorId(authorId);
    }

    @PostMapping()
    public OfferDto saveOffer(@RequestBody OfferDto offerDto,
                              @RequestHeader ("Authorization") String author) {
        System.out.println(author.substring(7));
        return advertisementService.save(offerDto, author.substring(7));
    }

    @Secured(ADMIN)
    @DeleteMapping("{id}")
    public void deleteOffer(@PathVariable Long id) {
        advertisementService.delete(id);
    }
}
