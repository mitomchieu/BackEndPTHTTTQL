package com.backend.template.common.exception.handler;

import com.backend.template.common.exception.ApiError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CustomRestExceptionHandler {
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<String> handleAll(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<String>(apiError.getMessage(), apiError.getStatus());
    }
}