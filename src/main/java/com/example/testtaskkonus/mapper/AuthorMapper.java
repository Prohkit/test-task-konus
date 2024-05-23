package com.example.testtaskkonus.mapper;

import com.example.testtaskkonus.domain.Author;
import com.example.testtaskkonus.dto.request.AddAuthorRequest;
import com.example.testtaskkonus.dto.response.AuthorResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author addAuthorRequestToAuthor(AddAuthorRequest addAuthorRequest);

    AuthorResponse toAuthorResponse(Author author);

    List<AuthorResponse> toAuthorResponseList(List<Author> author);
}
