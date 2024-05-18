package com.example.testtaskkonus.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddAuthorsToBookRequest {
    @JsonProperty("author_ids")
    private List<Long> authorIds;
}
