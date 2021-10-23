package com.example.main.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, code = HttpStatus.NOT_FOUND, reason = "Advertisement not found")
public class AdvertisementNotFoundException extends RuntimeException{
}
