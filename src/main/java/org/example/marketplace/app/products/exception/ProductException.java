package org.example.marketplace.app.products.exception;

import org.example.marketplace.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public enum ProductException implements BaseException {
    product_not_found(HttpStatus.NOT_FOUND, "Product not found");

    private final HttpStatus status;
    private final String message;

    ProductException(HttpStatus status, String message) {
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

