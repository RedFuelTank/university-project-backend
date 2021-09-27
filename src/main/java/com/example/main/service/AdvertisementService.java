package com.example.main.service;

import com.example.main.dto.OfferDto;
import com.example.main.dto.RequestDto;
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
        return advertisementRepository.getAllOffers().stream().map(this::convertAdvertisementToOffer).collect(Collectors.toList());
    }

    public List<RequestDto> getRequests() {
        return advertisementRepository.getAllRequests().stream().map(this::convertAdvertisementToRequest).collect(Collectors.toList());
    }

    private RequestDto convertAdvertisementToRequest(Advertisement advertisement) {
        return new RequestDto(advertisement.getId(), advertisement.getTitle(), advertisement.getDescription(), advertisement.getAuthorId());
    }

    private OfferDto convertAdvertisementToOffer(Advertisement advertisement) {
        return new OfferDto(advertisement.getId(), advertisement.getTitle(), advertisement.getDescription(), advertisement.getAuthorId());
    }

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }


}
