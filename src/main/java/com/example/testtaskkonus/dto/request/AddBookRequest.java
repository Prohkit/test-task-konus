package com.example.testtaskkonus.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddBookRequest {
    @NotNull
    private String title;

    @JsonProperty("author_ids")
    private List<Long> authorIds;

    private String isbn;

    @JsonProperty("year_of_publishing")
    private Integer yearOfPublishing;

    @JsonProperty("number_of_pages")
    private Integer numberOfPages;
}
