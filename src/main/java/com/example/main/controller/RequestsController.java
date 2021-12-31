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

import static com.example.main.config.security.ApplicationRoles.*;

@Secured(USER)
@RequestMapping("/requests")
@RestController
public class RequestsController {
    private final AdvertisementService advertisementService;
    private final UserService userService;

    @Autowired
    public RequestsController(AdvertisementService advertisementService, UserService userService) {
        this.advertisementService = advertisementService;
        this.userService = userService;
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

    @Secured(ADMIN)
    @DeleteMapping("{id}")
    public void deleteOffer(@PathVariable Long id) {
        advertisementService.delete(id);
    }
}
