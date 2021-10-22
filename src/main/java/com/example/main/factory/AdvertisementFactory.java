package com.example.main.factory;

import com.example.main.dto.OfferDto;
import com.example.main.dto.RequestDto;
import com.example.main.model.Advertisement;

public class AdvertisementFactory {
  public static OfferDto createOfferDto(Advertisement advertisement) {
    return new OfferDto(advertisement.getId(), advertisement.getTitle(), advertisement.getDescription(),
      advertisement.getAuthorId());
  }

  public static Advertisement createOffer(OfferDto offerDto) {
    return new Advertisement(offerDto.getTitle(), offerDto.getDescription(), offerDto.getAuthorId(), offerDto.getLat(), offerDto.getLng(), offerDto.getAddress(), Advertisement.Type.OFFER);
  }

  public static RequestDto createRequestDto(Advertisement advertisement) {
    return new RequestDto(advertisement.getId(), advertisement.getTitle(), advertisement.getDescription(),
      advertisement.getAuthorId());
  }

  public static Advertisement createRequest(RequestDto requestDto) {
    return new Advertisement(requestDto.getTitle(), requestDto.getDescription(), requestDto.getAuthorId(), requestDto.getLat(), requestDto.getLng(), requestDto.getAddress(),
      Advertisement.Type.REQUEST);
  }
}
