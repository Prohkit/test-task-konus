package com.example.testtaskkonus.service;

import com.example.testtaskkonus.domain.Author;
import com.example.testtaskkonus.dto.request.AddAuthorRequest;
import com.example.testtaskkonus.dto.request.ChangeAuthorRequest;
import com.example.testtaskkonus.dto.response.AuthorResponse;

import java.util.List;

public interface AuthorService {

    AuthorResponse addAuthor(AddAuthorRequest addAuthorRequest);

    void deleteAuthor(Long id);

    AuthorResponse changeAuthor(ChangeAuthorRequest changeAuthorRequest, Long id);

    List<AuthorResponse> getAllAuthors();

    Author getAuthorById(Long id);

    List<Author> getAuthorsByIds(List<Long> ids);
}
