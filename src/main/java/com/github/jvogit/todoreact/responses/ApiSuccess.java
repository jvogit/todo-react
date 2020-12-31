package com.github.jvogit.todoreact.responses;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiSuccess extends ApiResponse {
    
    public ApiSuccess() {
        super(HttpStatus.OK, "success");
    }
    
}
