package com.example.testtaskkonus.controller;

import com.example.testtaskkonus.dto.request.AddAuthorRequest;
import com.example.testtaskkonus.dto.request.ChangeAuthorRequest;
import com.example.testtaskkonus.dto.response.AuthorResponse;
import com.example.testtaskkonus.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> addAuthor(@RequestBody @Validated AddAuthorRequest addAuthorRequest) {
        AuthorResponse authorResponse = authorService.addAuthor(addAuthorRequest);
        return new ResponseEntity<>(authorResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

    @PatchMapping("change/{id}")
    public ResponseEntity<AuthorResponse> changeAuthorDetails(@RequestBody ChangeAuthorRequest changeAuthorRequest,
                                                              @PathVariable Long id) {
        AuthorResponse authorResponse = authorService.changeAuthor(changeAuthorRequest, id);
        return new ResponseEntity<>(authorResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        List<AuthorResponse> authorResponses = authorService.getAllAuthors();
        return new ResponseEntity<>(authorResponses, HttpStatus.OK);
    }
}
