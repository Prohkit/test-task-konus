package com.example.testtaskkonus.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeBookRequest {
    private String title;

    private String isbn;

    private Integer yearOfPublishing;

    private Integer numberOfPages;
}
