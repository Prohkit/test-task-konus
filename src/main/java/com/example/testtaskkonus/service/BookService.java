package com.example.testtaskkonus.service;

import com.example.testtaskkonus.domain.Author;
import com.example.testtaskkonus.domain.Book;
import com.example.testtaskkonus.dto.request.AddBookRequest;
import com.example.testtaskkonus.dto.request.ChangeBookRequest;
import com.example.testtaskkonus.dto.response.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse addBook(AddBookRequest addBookRequest, List<Author> authors);

    void deleteBook(Long id);

    BookResponse changeBook(ChangeBookRequest changeBookRequest, Long bookId);

    BookResponse addAuthorsToBook(Long bookId, List<Author> authors);

    BookResponse removeAuthorsFromBook(Long bookId, List<Author> authors);

    Book getBookById(Long id);

    List<BookResponse> getBooksFilteredByTitle(String title);

    BookResponse getBooksFilteredByIsbn(String isbn);

    List<BookResponse> getBooksFilteredByAuthor(Author author);

    List<BookResponse> getBooksFilteredByTitleIsbnAuthor(String title, String isbn, Long authorId);
}
