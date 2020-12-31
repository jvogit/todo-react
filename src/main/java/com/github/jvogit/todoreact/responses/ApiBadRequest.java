package com.github.jvogit.todoreact.responses;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiBadRequest extends ApiResponse {
    
    public ApiBadRequest() {
        this("Bad request");
    }
    
    public ApiBadRequest(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
    
}
