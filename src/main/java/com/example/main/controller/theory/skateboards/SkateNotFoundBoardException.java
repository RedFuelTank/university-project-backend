package com.example.main.controller.theory.skateboards;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, code = HttpStatus.NOT_FOUND, reason = "Skate not found")
public class SkateNotFoundBoardException extends RuntimeException {
}