package com.example.testtaskkonus.service;

import com.example.testtaskkonus.dto.request.AddAuthorRequest;
import com.example.testtaskkonus.dto.response.AuthorResponse;

public interface AuthorService {

    AuthorResponse addAuthor(AddAuthorRequest addAuthorRequest);

    void deleteAuthor(Long id);
}
