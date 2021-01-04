package com.github.jvogit.todoreact.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.jvogit.todoreact.response.ApiError;
import com.github.jvogit.todoreact.response.ApiResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ApiError(ex.getMessage()).toResponseEntity();
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ApiError(ex.getMessage()).toResponseEntity();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ApiError(ex.getMessage()).toResponseEntity();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ApiError(ex.getMessage()).toResponseEntity();
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<?> handleBadRequest(AuthenticationException ex) {
        return new ApiResponse(HttpStatus.UNAUTHORIZED, ex.getMessage())
                .toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleBadRequest(Exception ex) {
        return new ApiError(ex.getMessage()).toResponseEntity();
    }

}
