package com.example.testtaskkonus.repository;

import com.example.testtaskkonus.domain.Author;
import com.example.testtaskkonus.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByTitle(String title);

    Book findBookByIsbn(String isnb);

    List<Book> findBooksByAuthors(List<Author> authors);
}
