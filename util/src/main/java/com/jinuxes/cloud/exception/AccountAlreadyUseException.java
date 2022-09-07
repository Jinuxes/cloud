package com.jinuxes.cloud.exception;

public class AccountAlreadyUseException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public AccountAlreadyUseException() {
    }

    public AccountAlreadyUseException(String message) {
        super(message);
    }

    public AccountAlreadyUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountAlreadyUseException(Throwable cause) {
        super(cause);
    }

    public AccountAlreadyUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
