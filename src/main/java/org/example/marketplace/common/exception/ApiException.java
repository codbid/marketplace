package org.example.marketplace.common.exception;

public class ApiException extends RuntimeException{
    public final BaseException exception;

    public ApiException(BaseException exception) {
        super(exception.getMessage());
        this.exception = exception;
    }

    public BaseException getException() {
        return exception;
    }
}
