package com.github.jvogit.todoreact.responses;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiError extends ApiResponse {
    
    public ApiError() {
        this("An error has occured");
    }
    
    public ApiError(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
    
}
