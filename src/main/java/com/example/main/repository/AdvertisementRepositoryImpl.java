package com.example.main.repository;

import com.example.main.model.Advertisement;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class AdvertisementRepositoryImpl implements AdvertisementRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Advertisement> getAllOffers() {
        Query query = entityManager.createQuery("select u from Advertisement u where u.type = 1", Advertisement.class);
        return query.getResultList();
    }

    @Override
    public List<Advertisement> getAllRequests() {
        Query query = entityManager.createQuery("select u from Advertisement u where u.type = 0", Advertisement.class);
        return query.getResultList();
    }

    @Override
    public List<Advertisement> getAllOffersByAuthorId(Long authorId) {
        Query query = entityManager.createQuery("select u from Advertisement u where u.type = 1 and u.authorId = ?1", Advertisement.class);
        query.setParameter(1, authorId);
        return query.getResultList();
    }

    @Override
    public List<Advertisement> getAllRequestByAuthorId(Long authorId) {
        Query query = entityManager.createQuery("select u from Advertisement u where u.type = 0 and u.authorId = ?1", Advertisement.class);
        query.setParameter(1, authorId);
        return query.getResultList();
    }
}
