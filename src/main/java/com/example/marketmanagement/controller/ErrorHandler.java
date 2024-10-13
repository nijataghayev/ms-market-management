package com.example.marketmanagement.controller;

import com.example.marketmanagement.exception.NotFoundException;
import com.example.marketmanagement.model.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: nijataghayev
 */

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handle(NotFoundException e) {
        log.error(e.getMessage());
        return new ExceptionDto(e.getCode());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ExceptionDto> handle(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        List<ExceptionDto> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();
            errors.add(new ExceptionDto(field + ": " + message));
        });

        return errors;
    }
}
