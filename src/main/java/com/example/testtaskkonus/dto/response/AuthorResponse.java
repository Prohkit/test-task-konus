package com.example.testtaskkonus.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
    private Long id;

    private String lastName;

    private String firstName;

    private String patronymic;

    private LocalDate birthday;
}
