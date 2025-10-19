package org.example.marketplace.common.exception;

import org.springframework.http.HttpStatus;

public interface BaseException {
    HttpStatus getStatus();
    String getMessage();
}
