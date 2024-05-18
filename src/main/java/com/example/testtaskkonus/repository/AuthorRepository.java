package com.example.testtaskkonus.repository;

import com.example.testtaskkonus.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
