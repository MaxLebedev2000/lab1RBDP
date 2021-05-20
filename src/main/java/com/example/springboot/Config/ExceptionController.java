package com.example.springboot.Config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.transaction.SystemException;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleConflict(EntityNotFoundException ex, WebRequest request) {
        String bodyOfResponse = "Сущность с id " + ex.getMessage() + " не найдена.";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({SystemException.class})
    protected ResponseEntity<Object> handleSystemException(SystemException ex, WebRequest request) {
        String bodyOfResponse = "Произошла ошибка приоткате транзакции.";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}