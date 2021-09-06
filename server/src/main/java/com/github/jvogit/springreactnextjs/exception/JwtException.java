package com.github.jvogit.springreactnextjs.exception;

public class JwtException extends RuntimeException {
    public JwtException(final String message) {
        super(message);
    }
}
