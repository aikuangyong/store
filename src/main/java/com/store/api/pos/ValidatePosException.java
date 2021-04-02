package com.store.api.pos;

public class ValidatePosException extends Exception {

    public ValidatePosException(String message) {
        super(message);
    }

    public ValidatePosException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatePosException(Throwable cause) {
        super(cause);
    }

    protected ValidatePosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}