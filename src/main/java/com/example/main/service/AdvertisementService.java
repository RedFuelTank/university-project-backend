package com.example.main.service;

import com.example.main.config.security.jwt.JwtTokenProvider;
import com.example.main.dto.AdvertisementDto;
import com.example.main.dto.OfferDto;
import com.example.main.dto.RequestDto;
import com.example.main.factory.AdvertisementFactory;
import com.example.main.filter.AdvertisementFilter;
import com.example.main.filter.exception.IncorrectDateException;
import com.example.main.model.Advertisement;
import com.example.main.repository.AdvertisementRepository;
import com.example.main.repository.UserRepository;
import com.example.main.service.exception.AdvertisementNotFoundException;
import com.example.main.service.exception.PageNotFoundException;
import com.example.main.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    public static final int ADVERTISEMENTS_ON_ONE_PAGE = 16;
    private AdvertisementRepository advertisementRepository;
    private UserService userService;
    private final AdvertisementFilter filter = new AdvertisementFilter();

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository, UserService userService) {
        this.advertisementRepository = advertisementRepository;
        this.userService = userService;
    }

    public List<OfferDto> getOffers(Optional<Integer> page,
                                    Optional<String> startDate,
                                    Optional<String> expireDate) {

        List<Advertisement> offers = getFilteredAdvertisements(advertisementRepository.getAllOffers(),
                page, startDate, expireDate);
        return offers.stream().map(this::convertAdvertisementToOfferDto).collect(Collectors.toList());
    }

    public List<RequestDto> getRequests(Optional<Integer> page,
                                        Optional<String> startDate,
                                        Optional<String> expireDate) {
        List<Advertisement> requests = getFilteredAdvertisements(advertisementRepository.getAllRequests(),
                page, startDate, expireDate);
        return requests.stream().map(this::convertAdvertisementToRequestDto).collect(Collectors.toList());
    }

    private List<Advertisement> getFilteredAdvertisements(List<Advertisement> ads,
                                                          Optional<Integer> page,
                                                          Optional<String> startDate,
                                                          Optional<String> expireDate) {
        if (startDate.isPresent()) {
            try {
                ads = filter.filterByStartDate(ads, startDate.get());
            } catch (IncorrectDateException e) {
                System.out.println("Incorrect start date.");
            }
        }
        if (expireDate.isPresent()) {
            try {
                ads = filter.filterByExpirationDate(ads, expireDate.get());
            } catch (IncorrectDateException e) {
                System.out.println("Incorrect expiration date.");
            }
        }
        if (page.isPresent()) {
            ads = getAdvertisementsByPage(ads, page.get());
        }

        return ads;
    }

    private RequestDto convertAdvertisementToRequestDto(Advertisement advertisement) {
        return AdvertisementFactory.createRequestDto(advertisement);
    }

    private OfferDto convertAdvertisementToOfferDto(Advertisement advertisement) {
        return AdvertisementFactory.createOfferDto(advertisement);
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

    public OfferDto save(OfferDto offerDto, String token) {
        String authorUsername = tokenProvider.getUsernameFromToken(token);
        System.out.println(authorUsername);
        Advertisement offer = advertisementRepository.save(AdvertisementFactory.createOffer(offerDto,
                userRepository.findByUsername(authorUsername).get(0).getId()));
        return (OfferDto) findById(offer.getId());
    }

    public RequestDto save(RequestDto requestDto, String token) {
        String authorUsername = tokenProvider.getUsernameFromToken(token);
        Advertisement request = advertisementRepository.save(AdvertisementFactory.createRequest(requestDto,
                userRepository.findByUsername(authorUsername).get(0).getId()));
        return (RequestDto) findById(request.getId());
    }

    public void delete(Long id) {
        advertisementRepository.deleteById(id);
    }

    public List<OfferDto> getOffersByAuthorId(Long authorId) {
        List<Advertisement> offers = advertisementRepository.getAllOffersByAuthorId(authorId);
        return offers.stream().map(this::convertAdvertisementToOfferDto).collect(Collectors.toList());
    }

    public List<RequestDto> getRequestsByAuthorId(Long authorId) {
        List<Advertisement> requests = advertisementRepository.getAllRequestByAuthorId(authorId);
        return requests.stream().map(this::convertAdvertisementToRequestDto).collect(Collectors.toList());
    }
}
