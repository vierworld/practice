package ru.vw.practice.lesson5.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class CustomNotFoundException extends RuntimeException {
}
