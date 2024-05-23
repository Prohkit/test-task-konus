package com.example.testtaskkonus.mapper;

import com.example.testtaskkonus.domain.Author;
import com.example.testtaskkonus.domain.Book;
import com.example.testtaskkonus.dto.request.AddBookRequest;
import com.example.testtaskkonus.dto.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "authors", expression = "java(getEmptyArrayList())")
    Book addBookRequestToBook(AddBookRequest addBookRequest);

    default List<Author> getEmptyArrayList() {
        return new ArrayList<>();
    }

    BookResponse toBookResponse(Book book);

    List<BookResponse> toBookResponseList(List<Book> book);
}
