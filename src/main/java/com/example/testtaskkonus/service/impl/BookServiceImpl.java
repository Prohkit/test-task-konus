package com.example.testtaskkonus.service.impl;

import com.example.testtaskkonus.domain.Author;
import com.example.testtaskkonus.domain.Book;
import com.example.testtaskkonus.dto.request.AddBookRequest;
import com.example.testtaskkonus.dto.request.ChangeBookRequest;
import com.example.testtaskkonus.dto.response.BookResponse;
import com.example.testtaskkonus.exception.AuthorIsAlreadyConnectedToBookException;
import com.example.testtaskkonus.exception.BookNotFoundException;
import com.example.testtaskkonus.repository.BookRepository;
import com.example.testtaskkonus.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookResponse addBook(AddBookRequest addBookRequest, List<Author> authors) {
        Book bookToAdd = Book.builder()
                .title(addBookRequest.getTitle())
                .authors(new ArrayList<>())
                .isbn(addBookRequest.getIsbn())
                .yearOfPublishing(addBookRequest.getYearOfPublishing())
                .numberOfPages(addBookRequest.getNumberOfPages())
                .build();
        for (Author author : authors) {
            bookToAdd.addAuthor(author);
        }
        Book book = bookRepository.save(bookToAdd);

        return createBookResponseFromBook(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        List<Author> authors = book.getAuthors();
        for (Author author : authors) {
            book.removeAuthor(author);
        }
        bookRepository.delete(book);
    }

    @Override
    public BookResponse changeBook(ChangeBookRequest changeBookRequest, Long bookId) {
        Book bookToUpdate = getBookById(bookId);
        setBookFieldsIfNotNull(bookToUpdate, changeBookRequest);
        bookRepository.save(bookToUpdate);
        return createBookResponseFromBook(bookToUpdate);
    }

    @Override
    public BookResponse addAuthorsToBook(Long bookId, List<Author> authors) {
        Book book = getBookById(bookId);
        for (Author author : authors) {
            if (book.isAuthorAlreadyConnected(author)) {
                throw new AuthorIsAlreadyConnectedToBookException(author.getId(), bookId);
            }
            book.addAuthor(author);
        }
        Book toBookResponse = bookRepository.save(book);
        return createBookResponseFromBook(toBookResponse);
    }

    @Override
    public BookResponse removeAuthorsFromBook(Long bookId, List<Author> authors) {
        Book book = getBookById(bookId);
        for (Author author : authors) {
            book.removeAuthor(author);
        }
        Book toBookResponse = bookRepository.save(book);
        return createBookResponseFromBook(toBookResponse);
    }

    private void setBookFieldsIfNotNull(Book book, ChangeBookRequest changeBookRequest) {
        if (changeBookRequest.getTitle() != null) {
            book.setTitle(changeBookRequest.getTitle());
        }
        if (changeBookRequest.getIsbn() != null) {
            book.setIsbn(changeBookRequest.getIsbn());
        }
        if (changeBookRequest.getYearOfPublishing() != null) {
            book.setYearOfPublishing(changeBookRequest.getYearOfPublishing());
        }
        if (changeBookRequest.getNumberOfPages() != null) {
            book.setNumberOfPages(changeBookRequest.getNumberOfPages());
        }
    }

    @Override
    public List<BookResponse> getBooksFilteredByTitle(String title) {
        List<Book> books = bookRepository.findBooksByTitle(title);
        return books.stream().map(book -> new BookResponse(
                book.getId(), book.getTitle(), book.getIsbn(),
                book.getYearOfPublishing(), book.getNumberOfPages())).toList();
    }

    @Override
    public BookResponse getBooksFilteredByIsbn(String isbn) {
        Book book = bookRepository.findBookByIsbn(isbn);
        return createBookResponseFromBook(book);
    }

    @Override
    public List<BookResponse> getBooksFilteredByAuthor(Author author) {
        List<Book> books = bookRepository.findBooksByAuthors(List.of(author));
        return books.stream().map(book -> new BookResponse(
                book.getId(), book.getTitle(), book.getIsbn(),
                book.getYearOfPublishing(), book.getNumberOfPages())).toList();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    private BookResponse createBookResponseFromBook(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .yearOfPublishing(book.getYearOfPublishing())
                .numberOfPages(book.getNumberOfPages())
                .build();
    }
}
