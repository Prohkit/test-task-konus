package com.example.testtaskkonus.exception.handler;

import com.example.testtaskkonus.exception.AuthorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AuthorNotFoundException.class)
    public ApiError authorNotFoundException(AuthorNotFoundException exception) {
        return getApiError(HttpStatus.NOT_FOUND, exception);
    }

    private ApiError getApiError(HttpStatus status, Exception exception) {
        return ApiError.builder()
                .status(status.value())
                .message(exception.getMessage())
                .reason(status.getReasonPhrase())
                .build();
    }
}
