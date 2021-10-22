package com.example.main.factory;

import com.example.main.dto.OfferDto;
import com.example.main.dto.RequestDto;
import com.example.main.model.Advertisement;

public class AdvertisementFactory {
  public static OfferDto createOfferDto(Advertisement advertisement) {
    OfferDto offerDto = new OfferDto(advertisement.getId(), advertisement.getTitle(), advertisement.getDescription(),
            advertisement.getAuthorId());
    offerDto.setLat(advertisement.getLat());
    offerDto.setLng(advertisement.getLng());
    offerDto.setAddress(advertisement.getAddress());
    return offerDto;
  }

  public static Advertisement createOffer(OfferDto offerDto) {
    return new Advertisement(offerDto.getTitle(), offerDto.getDescription(), offerDto.getAuthorId(), offerDto.getLat(), offerDto.getLng(), offerDto.getAddress(), Advertisement.Type.OFFER);
  }

  public static RequestDto createRequestDto(Advertisement advertisement) {
    RequestDto requestDto = new RequestDto(advertisement.getId(), advertisement.getTitle(), advertisement.getDescription(),
            advertisement.getAuthorId());
    requestDto.setLat(advertisement.getLat());
    requestDto.setLng(advertisement.getLng());
    requestDto.setAddress(advertisement.getAddress());
    return requestDto;
  }

  public static Advertisement createRequest(RequestDto requestDto) {
    return new Advertisement(requestDto.getTitle(), requestDto.getDescription(), requestDto.getAuthorId(), requestDto.getLat(), requestDto.getLng(), requestDto.getAddress(),
      Advertisement.Type.REQUEST);
  }
}
