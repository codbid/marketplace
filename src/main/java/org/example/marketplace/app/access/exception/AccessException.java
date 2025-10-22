package org.example.marketplace.app.access.exception;

import org.example.marketplace.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public enum AccessException implements BaseException {
    forbidden(HttpStatus.FORBIDDEN, "You do not have permissions to access this resource"),
    hidden_forbidden(HttpStatus.NOT_FOUND, "Resource not found"),
    user_not_verified(HttpStatus.FORBIDDEN, "You must be verified for create a seller profile. Please, contact us Administrator for more info");


    private final HttpStatus status;
    private final String message;

    AccessException(HttpStatus status, String message) {
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
