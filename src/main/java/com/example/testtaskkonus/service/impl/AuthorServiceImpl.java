package com.example.testtaskkonus.service.impl;

import com.example.testtaskkonus.domain.Author;
import com.example.testtaskkonus.dto.request.AddAuthorRequest;
import com.example.testtaskkonus.dto.response.AuthorResponse;
import com.example.testtaskkonus.exception.AuthorNotFoundException;
import com.example.testtaskkonus.repository.AuthorRepository;
import com.example.testtaskkonus.service.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorResponse addAuthor(AddAuthorRequest addAuthorRequest) {
        Author authorToAdd = Author.builder()
                .lastName(addAuthorRequest.getLastName())
                .firstName(addAuthorRequest.getFirstName())
                .patronymic(addAuthorRequest.getPatronymic())
                .birthday(addAuthorRequest.getBirthday())
                .build();
        Author author = authorRepository.save(authorToAdd);
        return AuthorResponse.builder()
                .id(author.getId())
                .lastName(author.getLastName())
                .firstName(author.getFirstName())
                .patronymic(author.getPatronymic())
                .birthday(author.getBirthday())
                .build();
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        authorRepository.delete(author);
    }
}
