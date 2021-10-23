package com.example.main.filter;

import com.example.main.filter.exception.IncorrectDateException;
import com.example.main.model.Advertisement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementFilter {
    private static final DateTimeFormatter DTM = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public List<Advertisement> filterByStartDate(List<Advertisement> ads, String startDate)
            throws IncorrectDateException {
        LocalDate date;
        try {
            date = LocalDate.parse(startDate, DTM);
        } catch (DateTimeParseException e) {
            throw new IncorrectDateException();
        }

        List<Advertisement> filteredList = new ArrayList<>();
        for (Advertisement ad : ads) {
            try {
                if (!LocalDate.parse(ad.getStartDate(), DTM).isBefore(date)) {
                    filteredList.add(ad);
                }
            } catch (DateTimeParseException ignored) {} // This exception can appear if for some reason date in
                                                        // an advertisement is incorrect. If this happens, we
                                                        // ignore this advertisement (and exception it created)
                                                        // and go to the next on in the list, as this one obviously
                                                        // does not suit us.
        }
        return filteredList;
    }

    public List<Advertisement> filterByExpirationDate(List<Advertisement> ads, String expireDate)
            throws IncorrectDateException {
        LocalDate date;
        try {
            date = LocalDate.parse(expireDate, DTM);
        } catch (DateTimeParseException e) {
            throw new IncorrectDateException();
        }

        List<Advertisement> filteredList = new ArrayList<>();
        for (Advertisement ad : ads) {
            try {
                if (!LocalDate.parse(ad.getExpirationDate(), DTM).isAfter(date)) {
                    filteredList.add(ad);
                }
            } catch (DateTimeParseException ignored) {} // This exception can appear if for some reason date in
                                                        // an advertisement is incorrect. If this happens, we
                                                        // ignore this advertisement (and exception it created)
                                                        // and go to the next on in the list, as this one obviously
                                                        // does not suit us.
        }
        return filteredList;
    }
}
