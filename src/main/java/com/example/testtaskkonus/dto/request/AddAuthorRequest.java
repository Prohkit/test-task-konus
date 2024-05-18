package com.example.testtaskkonus.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AddAuthorRequest {
    @JsonProperty("last_name")
    @NotNull
    private String lastName;

    @JsonProperty("first_name")
    @NotNull
    private String firstName;

    private String patronymic;

    private LocalDate birthday;
}
