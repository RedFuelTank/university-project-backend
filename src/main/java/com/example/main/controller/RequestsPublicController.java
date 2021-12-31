package com.example.main.controller;

import com.example.main.dto.RequestDto;
import com.example.main.service.AdvertisementService;
import com.example.main.service.UserService;
import com.example.main.service.exception.AdvertisementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.main.config.security.ApplicationRoles.ADMIN;
import static com.example.main.config.security.ApplicationRoles.USER;

@RequestMapping("/requests")
@RestController
public class RequestsPublicController {
    private final AdvertisementService advertisementService;
    private final UserService userService;

    @Autowired
    public RequestsPublicController(AdvertisementService advertisementService, UserService userService) {
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
}
