package org.example.marketplace.app.auth.exception;

import org.example.marketplace.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public enum AuthException implements BaseException {
    unauthorized(HttpStatus.UNAUTHORIZED, "");

    private final HttpStatus status;
    private final String message;

    AuthException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() { return status; }

    @Override
    public String getMessage() { return message; }
}
