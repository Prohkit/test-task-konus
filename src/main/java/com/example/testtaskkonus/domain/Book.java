package com.example.testtaskkonus.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors;

    private String title;

    private String isbn;

    private Integer yearOfPublishing;

    private Integer numberOfPages;
}
