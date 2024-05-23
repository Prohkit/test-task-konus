package com.example.testtaskkonus.repository;

import com.example.testtaskkonus.domain.Author;
import com.example.testtaskkonus.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findBooksByTitleIgnoreCase(String title);

    Book findBookByIsbnIgnoreCase(String isnb);

    List<Book> findBooksByAuthors(List<Author> authors);
}
