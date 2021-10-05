package com.example.main.service;

import com.example.main.dto.AdvertisementDto;
import com.example.main.dto.OfferDto;
import com.example.main.dto.RequestDto;
import com.example.main.factory.AdvertisementFactory;
import com.example.main.model.Advertisement;
import com.example.main.repository.AdvertisementRepository;
import com.example.main.service.exception.AdvertisementNotFoundException;
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

  public AdvertisementDto findById(Long id) {
    Advertisement advertisement = getDbAdvertisementById(id);

    return advertisement.getType() == Advertisement.Type.OFFER ? AdvertisementFactory.createOfferDto(advertisement) : AdvertisementFactory.createRequestDto(advertisement);
  }

  private Advertisement getDbAdvertisementById(Long id) {
    return advertisementRepository.findById(id).orElseThrow(AdvertisementNotFoundException::new);
  }

  public OfferDto save(OfferDto offerDto) {
    Advertisement offer = advertisementRepository.save(AdvertisementFactory.createOffer(offerDto));
    return (OfferDto) findById(offer.getId());
  }

  public RequestDto save(RequestDto requestDto) {
    Advertisement request = advertisementRepository.save(AdvertisementFactory.createRequest(requestDto));
    return (RequestDto) findById(request.getId());
  }

  public void delete(Long id) {
    advertisementRepository.deleteById(id);
  }


}
