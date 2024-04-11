package com.example.dockerproject.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> apiExceptionHandler(ApiException ex) {
        return ResponseEntity.status(ex.getApiErrorCode().getErrorCode()).body(ex.getErrorMessage());
    }

}
