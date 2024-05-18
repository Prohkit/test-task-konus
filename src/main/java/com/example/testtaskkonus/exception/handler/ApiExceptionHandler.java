package com.example.testtaskkonus.exception.handler;

import com.example.testtaskkonus.exception.AuthorIsAlreadyConnectedToBookException;
import com.example.testtaskkonus.exception.AuthorNotFoundException;
import com.example.testtaskkonus.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({AuthorNotFoundException.class, BookNotFoundException.class})
    public ApiError notFoundException(RuntimeException exception) {
        return getApiError(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(AuthorIsAlreadyConnectedToBookException.class)
    public ApiError authorIsAlreadyConnectedToBook(RuntimeException exception) {
        return getApiError(HttpStatus.BAD_REQUEST, exception);
    }

    private ApiError getApiError(HttpStatus status, Exception exception) {
        return ApiError.builder()
                .status(status.value())
                .message(exception.getMessage())
                .reason(status.getReasonPhrase())
                .build();
    }
}
