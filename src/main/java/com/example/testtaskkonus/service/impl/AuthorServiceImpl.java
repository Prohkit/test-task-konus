package com.example.testtaskkonus.service.impl;

import com.example.testtaskkonus.domain.Author;
import com.example.testtaskkonus.dto.request.AddAuthorRequest;
import com.example.testtaskkonus.dto.request.ChangeAuthorRequest;
import com.example.testtaskkonus.dto.response.AuthorResponse;
import com.example.testtaskkonus.exception.AuthorNotFoundException;
import com.example.testtaskkonus.repository.AuthorRepository;
import com.example.testtaskkonus.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public AuthorResponse changeAuthor(ChangeAuthorRequest changeAuthorRequest, Long id) {
        Author authorToUpdate = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
        setAuthorFieldsIfNotNull(authorToUpdate, changeAuthorRequest);
        authorRepository.save(authorToUpdate);
        return AuthorResponse.builder()
                .id(authorToUpdate.getId())
                .lastName(authorToUpdate.getLastName())
                .firstName(authorToUpdate.getFirstName())
                .patronymic(authorToUpdate.getPatronymic())
                .birthday(authorToUpdate.getBirthday())
                .build();
    }

    private void setAuthorFieldsIfNotNull(Author authorToUpdate, ChangeAuthorRequest changeAuthorRequest) {
        if (changeAuthorRequest.getLastName() != null) {
            authorToUpdate.setLastName(changeAuthorRequest.getLastName());
        }
        if (changeAuthorRequest.getFirstName() != null) {
            authorToUpdate.setFirstName(changeAuthorRequest.getFirstName());
        }
        if (changeAuthorRequest.getPatronymic() != null) {
            authorToUpdate.setPatronymic(changeAuthorRequest.getPatronymic());
        }
        if (changeAuthorRequest.getBirthday() != null) {
            authorToUpdate.setBirthday(changeAuthorRequest.getBirthday());
        }
    }

    @Override
    public List<AuthorResponse> getAllAuthors() {
        List<Author> author = authorRepository.findAll();
        return author.stream().map(currentAuthor -> new AuthorResponse(
                currentAuthor.getId(), currentAuthor.getLastName(),
                currentAuthor.getFirstName(), currentAuthor.getPatronymic(),
                currentAuthor.getBirthday())).toList();
    }
}
