package com.example.main.service;

import com.example.main.dto.OfferDto;
import com.example.main.model.Advertisement;
import com.example.main.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    private AdvertisementRepository advertisementRepository;

    public List<OfferDto> getOffers() {
        return advertisementRepository.getAllOffers().stream().
                map(advertisement -> new OfferDto(advertisement.getId(), advertisement.getTitle(), advertisement.getDescription(), null)).
                collect(Collectors.toList());
    }

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }


}
