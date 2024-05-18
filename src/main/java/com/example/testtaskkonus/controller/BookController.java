package com.example.testtaskkonus.controller;

import com.example.testtaskkonus.domain.Author;
import com.example.testtaskkonus.dto.request.AddAuthorsToBookRequest;
import com.example.testtaskkonus.dto.request.AddBookRequest;
import com.example.testtaskkonus.dto.request.ChangeBookRequest;
import com.example.testtaskkonus.dto.request.RemoveAuthorsFromBookRequest;
import com.example.testtaskkonus.dto.response.BookResponse;
import com.example.testtaskkonus.service.AuthorService;
import com.example.testtaskkonus.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody @Validated AddBookRequest addBookRequest) {
        List<Author> authors;
        if (addBookRequest.getAuthorIds() != null) {
            authors = authorService.getAuthorsByIds(addBookRequest.getAuthorIds());
        } else {
            authors = new ArrayList<>();
        }
        BookResponse bookResponse = bookService.addBook(addBookRequest, authors);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @PatchMapping("{id}")
    public ResponseEntity<BookResponse> changeBookDetails(@RequestBody ChangeBookRequest changeBookRequest,
                                                          @PathVariable Long id) {
        BookResponse bookResponse = bookService.changeBook(changeBookRequest, id);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PutMapping("{id}/authors/add")
    public ResponseEntity<BookResponse> addAuthorsToBook(@RequestBody AddAuthorsToBookRequest request,
                                                         @PathVariable Long id) {
        List<Author> authors = authorService.getAuthorsByIds(request.getAuthorIds());
        BookResponse bookResponse = bookService.addAuthorsToBook(id, authors);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PutMapping("{id}/authors/remove")
    public ResponseEntity<BookResponse> removeAuthorsFromBook(@RequestBody RemoveAuthorsFromBookRequest request,
                                                         @PathVariable Long id) {
        List<Author> authors = authorService.getAuthorsByIds(request.getAuthorIds());
        BookResponse bookResponse = bookService.removeAuthorsFromBook(id, authors);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @GetMapping("/filtered/title/{title}")
    public ResponseEntity<List<BookResponse>> getBooksFilteredByTitle(@PathVariable String title) {
        List<BookResponse> bookResponses = bookService.getBooksFilteredByTitle(title);
        return new ResponseEntity<>(bookResponses, HttpStatus.OK);
    }

    @GetMapping("/filtered/isnb/{isnb}")
    public ResponseEntity<BookResponse> getBooksFilteredByIsnb(@PathVariable String isnb) {
        BookResponse bookResponse = bookService.getBooksFilteredByIsbn(isnb);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @GetMapping("/filtered/author/{id}")
    public ResponseEntity<List<BookResponse>> getBooksFilteredByTitle(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        List<BookResponse> bookResponses = bookService.getBooksFilteredByAuthor(author);
        return new ResponseEntity<>(bookResponses, HttpStatus.OK);
    }
}
