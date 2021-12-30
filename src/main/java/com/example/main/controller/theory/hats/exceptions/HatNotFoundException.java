package com.example.main.controller.theory.hats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, code = HttpStatus.NOT_FOUND, reason = "Hat not found")
public class HatNotFoundException extends RuntimeException {
}
