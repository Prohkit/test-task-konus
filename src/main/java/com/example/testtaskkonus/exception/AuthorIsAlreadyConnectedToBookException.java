package com.example.testtaskkonus.exception;

public class AuthorIsAlreadyConnectedToBookException extends RuntimeException {
    public AuthorIsAlreadyConnectedToBookException(Long authorId, Long bookId) {
        super("Author with id " + authorId + " is already connected to book with id " + bookId);
    }
}
