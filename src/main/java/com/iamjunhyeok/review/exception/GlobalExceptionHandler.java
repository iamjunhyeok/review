package com.iamjunhyeok.review.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
        log.error("ApplicationException!!! httpStatus={}, message={}", e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
    }

    private record ErrorResponse(String message) {
    }
}
