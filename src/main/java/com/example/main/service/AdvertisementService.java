package com.example.main.service;

import com.example.main.dto.OfferDto;
import com.example.main.dto.RequestDto;
import com.example.main.factory.AdvertisementFactory;
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
        return advertisementRepository.getAllOffers().stream()
          .map(this::convertAdvertisementToOfferDto).collect(Collectors.toList());
    }

    public List<RequestDto> getRequests() {
        return advertisementRepository.getAllRequests().stream()
          .map(this::convertAdvertisementToRequestDto).collect(Collectors.toList());
    }

    private RequestDto convertAdvertisementToRequestDto(Advertisement advertisement) {
        return AdvertisementFactory.createRequestDto(advertisement);
    }

    private OfferDto convertAdvertisementToOfferDto(Advertisement advertisement) {
        return AdvertisementFactory.createOfferDto(advertisement);
    }

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }


}
