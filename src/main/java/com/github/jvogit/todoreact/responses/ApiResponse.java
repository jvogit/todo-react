package com.github.jvogit.todoreact.responses;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiResponse {
    private final HttpStatus status;
    private final String message;
}
