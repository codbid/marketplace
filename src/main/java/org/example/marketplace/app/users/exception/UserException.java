package org.example.marketplace.app.users.exception;

import org.example.marketplace.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public enum UserException implements BaseException {
    user_not_found(HttpStatus.NOT_FOUND, "User not found"),
    email_already_exists(HttpStatus.CONFLICT, "Email already exists");

    private final HttpStatus status;
    private final String message;

    UserException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
