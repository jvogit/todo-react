package com.github.jvogit.todoreact.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Data;

@Data
public class ApiResponse {
    private final HttpStatus status;
    private final String message;
    
    public ResponseEntity<Object> toResponseEntity() {
        return ResponseEntity.status(status).body(this);
    }
    
}
