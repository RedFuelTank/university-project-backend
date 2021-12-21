package com.example.main.controller;

import com.example.main.config.security.ApplicationRoles;
import com.example.main.dto.RequestDto;
import com.example.main.service.AdvertisementService;
import com.example.main.service.UserService;
import com.example.main.service.exception.AdvertisementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/requests")
@RestController
public class RequestsController {
    private final AdvertisementService advertisementService;
    private final UserService userService;

    @GetMapping("forAdmin")
    @Secured(ApplicationRoles.ADMIN)
    public String getSmth() {
        return "admin";
    }

    @GetMapping("forUsers")
    @Secured({ApplicationRoles.ADMIN, ApplicationRoles.USER})
    public String getSmth1() {
        return "user";
    }

    @GetMapping("forEverybody")
    @Secured({ApplicationRoles.ADMIN, ApplicationRoles.USER, "ROLE_ANONYMOUS"})
    public String getForAll() {
        return "Everybody can access";
    }

    @Autowired
    public RequestsController(AdvertisementService advertisementService, UserService userService) {
        this.advertisementService = advertisementService;
        this.userService = userService;
    }

    @GetMapping
    public List<RequestDto> get(@RequestParam Optional<Integer> page,
                                @RequestParam(required = false) Optional<String> startDate,
                                @RequestParam(required = false) Optional<String> expireDate) {
        List<RequestDto> requests = advertisementService.getRequests(page, startDate, expireDate);
        requests.forEach(request -> request.updateUserInfo(userService.findById((long) request.getAuthorId())));
        return requests;
    }

    @GetMapping("/authorId/{authorId}")
    public List<RequestDto> getByAuthorId(@PathVariable Long authorId) {
        return advertisementService.getRequestsByAuthorId(authorId);
    }

    @GetMapping("/{id}")
    public RequestDto getById(@PathVariable Long id) {
        try {
            RequestDto request = (RequestDto) advertisementService.findById(id);
            request.updateUserInfo(userService.findById((long) request.getAuthorId()));
            return request;
        } catch (ClassCastException e) {
            throw new AdvertisementNotFoundException();
        }
    }

    @PostMapping()
    public RequestDto saveOffer(@RequestBody RequestDto requestDto) {
        return advertisementService.save(requestDto);
    }

    @DeleteMapping("{id}")
    public void deleteOffer(@PathVariable Long id) {
        advertisementService.delete(id);
    }
}
