package org.example.marketplace.app.seller.staff.exception;

import org.example.marketplace.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public enum ServiceStaffException implements BaseException {
    user_already_employed(HttpStatus.CONFLICT, "User is already employed to this seller");

    private final HttpStatus status;
    private final String message;

    ServiceStaffException(HttpStatus status, String message) {
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

