package com.example.main.filter;

import com.example.main.filter.exception.IncorrectDateException;
import com.example.main.model.Advertisement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdvertisementFilterTests {

    AdvertisementFilter filter = new AdvertisementFilter();
    static List<Advertisement> ads = new ArrayList<>();

    @BeforeAll
    static void createAds() {
        ads = List.of(new Advertisement("Request-2022", "Request4", 1L, 0, 0, "", "25-10-2022", Advertisement.Type.REQUEST),
                new Advertisement("Request-2021", "Request-2021", 1L, 0, 0, "", "25-10-2021", Advertisement.Type.REQUEST),
                new Advertisement("Request-2024", "Request-2024", 1L, 0, 0, "", "25-10-2024", Advertisement.Type.REQUEST));

        ads.forEach(ad -> ad.setStartDate("20-10-2021"));
    }

    @Test
    void testFilterByExpirationDateSimple() throws IncorrectDateException {
        assertEquals(ads.subList(0, 2), filter.filterByExpirationDate(ads, "20-10-2023"));
        assertEquals(ads.subList(1, 2), filter.filterByExpirationDate(ads, "29-10-2021"));
    }

    @Test
    void testFilterByExpirationDateGivesExceptionDateIsIncorrect() {
        assertThrows(IncorrectDateException.class, () -> filter.filterByExpirationDate(ads, "20052023"));
        assertThrows(IncorrectDateException.class, () -> filter.filterByExpirationDate(ads, "39-05-2023"));
        assertThrows(IncorrectDateException.class, () -> filter.filterByExpirationDate(ads, "20-100-2023"));
    }
}
