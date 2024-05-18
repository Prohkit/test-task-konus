package com.example.testtaskkonus.exception.handler;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiError {
    private String message;
    private String reason;
    private Integer status;
}
