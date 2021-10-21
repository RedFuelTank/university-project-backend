package com.example.main.controller;

import com.example.main.dto.RequestDto;
import com.example.main.service.AdvertisementService;
import com.example.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
  public List<RequestDto> get() {
    List<RequestDto> requests = advertisementService.getRequests();
    requests.forEach(request -> request.updateUserInfo(userService.findById((long) request.getAuthorId())));
    return requests;
  }

  @GetMapping("/{id}")
  public RequestDto getById(@PathVariable Long id) {
    RequestDto request = (RequestDto) advertisementService.findById(id);
    request.updateUserInfo(userService.findById((long) request.getAuthorId()));
    return request;
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
