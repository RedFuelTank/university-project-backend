package com.example.main.service;

import com.example.main.dto.AdvertisementDto;
import com.example.main.dto.OfferDto;
import com.example.main.dto.RequestDto;
import com.example.main.factory.AdvertisementFactory;
import com.example.main.model.Advertisement;
import com.example.main.repository.AdvertisementRepository;
import com.example.main.service.exception.AdvertisementNotFoundException;
import com.example.main.service.exception.PageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
  public static final int ADVERTISEMENTS_ON_ONE_PAGE = 15;
  private AdvertisementRepository advertisementRepository;
  private UserService userService;

  public List<OfferDto> getOffers(Optional<Integer> page) {
    if (page.isPresent()) {
      return getOffersByPage(page.get());
    }

    return advertisementRepository.getAllOffers().stream()
      .map(this::convertAdvertisementToOfferDto).collect(Collectors.toList());
  }

  public List<RequestDto> getRequests(Optional<Integer> page) {
    if (page.isPresent()) {
      return getRequestsByPage(page.get());
    }

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
  public AdvertisementService(AdvertisementRepository advertisementRepository, UserService userService) {
    this.advertisementRepository = advertisementRepository;
    this.userService = userService;
  }

  public AdvertisementDto findById(Long id) {
    Advertisement advertisement = getDbAdvertisementById(id);

    return advertisement.getType() == Advertisement.Type.OFFER ? AdvertisementFactory.createOfferDto(advertisement)
      : AdvertisementFactory.createRequestDto(advertisement);
  }

  private Advertisement getDbAdvertisementById(Long id) {
    return advertisementRepository.findById(id).orElseThrow(AdvertisementNotFoundException::new);
  }

  public List<Advertisement> getAdvertisementsByPage(List<Advertisement> ads, int page) {
    int startIndex = (page - 1) * ADVERTISEMENTS_ON_ONE_PAGE;
    int endIndex = page * ADVERTISEMENTS_ON_ONE_PAGE;

    if (startIndex >= ads.size()) {
      throw new PageNotFoundException();
    }

    if (endIndex >= ads.size()) {
      endIndex = ads.size();
    }

    return ads.subList(startIndex, endIndex);
  }

  public List<OfferDto> getOffersByPage(int page) {
    List<Advertisement> offers = getAdvertisementsByPage(advertisementRepository.getAllOffers(), page);
    List<OfferDto> offerDtos = offers.stream()
            .map(AdvertisementFactory::createOfferDto)
            .collect(Collectors.toList());
    offerDtos.forEach(o -> o.updateUserInfo(userService.findById((long) o.getAuthorId())));
    return offerDtos;
  }

  public List<RequestDto> getRequestsByPage(int page) {
    List<Advertisement> requests = getAdvertisementsByPage(advertisementRepository.getAllRequests(), page);
    List<RequestDto> requestDtos = requests.stream()
            .map(AdvertisementFactory::createRequestDto)
            .collect(Collectors.toList());
    requestDtos.forEach(r -> r.updateUserInfo(userService.findById((long) r.getAuthorId())));
    return requestDtos;
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
