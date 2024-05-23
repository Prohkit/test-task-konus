package com.example.testtaskkonus.service.impl;

import com.example.testtaskkonus.domain.Author;
import com.example.testtaskkonus.domain.Book;
import com.example.testtaskkonus.dto.request.AddBookRequest;
import com.example.testtaskkonus.dto.request.ChangeBookRequest;
import com.example.testtaskkonus.dto.response.BookResponse;
import com.example.testtaskkonus.exception.AuthorIsAlreadyConnectedToBookException;
import com.example.testtaskkonus.exception.BookNotFoundException;
import com.example.testtaskkonus.mapper.BookMapper;
import com.example.testtaskkonus.repository.BookRepository;
import com.example.testtaskkonus.service.BookService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookResponse addBook(AddBookRequest addBookRequest, List<Author> authors) {
        Book bookToAdd = bookMapper.addBookRequestToBook(addBookRequest);
        for (Author author : authors) {
            bookToAdd.addAuthor(author);
        }
        Book book = bookRepository.save(bookToAdd);

        return bookMapper.toBookResponse(book);
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
        return bookMapper.toBookResponse(bookToUpdate);
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
        return bookMapper.toBookResponse(toBookResponse);
    }

    @Override
    public BookResponse removeAuthorsFromBook(Long bookId, List<Author> authors) {
        Book book = getBookById(bookId);
        for (Author author : authors) {
            book.removeAuthor(author);
        }
        Book toBookResponse = bookRepository.save(book);
        return bookMapper.toBookResponse(toBookResponse);
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
    public List<BookResponse> getBooksFilteredByTitleIsbnAuthor(String title, String isbn, Long authorId) {
        if (title == null && isbn == null && authorId == null) {
            return new ArrayList<>();
        }
        List<Book> books = bookRepository.findAll((root, query, cb) -> {
            Predicate conjunction = cb.conjunction();
            if (Objects.nonNull(title)) {
                Predicate like = cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
                conjunction = cb.and(conjunction, like);
            }
            if (Objects.nonNull(isbn)) {
                Predicate like = cb.like(cb.upper(root.get("isbn")), "%" + isbn.toUpperCase() + "%");
                conjunction = cb.and(conjunction, like);
            }
            if (Objects.nonNull(authorId)) {
                Predicate equal = cb.equal(root.get("authorId"), authorId);
                conjunction = cb.and(conjunction, equal);
            }
            return conjunction;
        });
        return bookMapper.toBookResponseList(books);
    }

    @Override
    public List<BookResponse> getBooksFilteredByTitle(String title) {
        List<Book> books = bookRepository.findBooksByTitleIgnoreCase(title);
        return bookMapper.toBookResponseList(books);
    }

    @Override
    public BookResponse getBooksFilteredByIsbn(String isbn) {
        Book book = bookRepository.findBookByIsbnIgnoreCase(isbn);
        return bookMapper.toBookResponse(book);
    }

    @Override
    public List<BookResponse> getBooksFilteredByAuthor(Author author) {
        List<Book> books = bookRepository.findBooksByAuthors(List.of(author));
        return bookMapper.toBookResponseList(books);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }
}
