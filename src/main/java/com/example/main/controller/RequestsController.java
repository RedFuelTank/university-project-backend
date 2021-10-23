package com.example.main.controller;

import com.example.main.dto.RequestDto;
import com.example.main.service.AdvertisementService;
import com.example.main.service.UserService;
import com.example.main.service.exception.AdvertisementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
