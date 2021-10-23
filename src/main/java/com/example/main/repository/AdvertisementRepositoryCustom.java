package com.example.main.repository;

import com.example.main.model.Advertisement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepositoryCustom {
    List<Advertisement> getAllOffers();

    List<Advertisement> getAllRequests();

    List<Advertisement> getAllOffersByAuthorId(Long authorId);

    List<Advertisement> getAllRequestByAuthorId(Long authorId);
}
