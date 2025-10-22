package org.example.marketplace.app.seller.exception;

import org.example.marketplace.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public enum SellerException implements BaseException {
    seller_not_found(HttpStatus.NOT_FOUND, "Seller not found");

    private final HttpStatus status;
    private final String message;

    SellerException(HttpStatus status, String message) {
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

