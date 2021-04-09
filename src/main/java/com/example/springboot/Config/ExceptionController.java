package com.example.springboot.Config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleConflict(EntityNotFoundException ex, WebRequest request) {
        String bodyOfResponse = "Entity with id " + ex.getMessage() + " not found";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}