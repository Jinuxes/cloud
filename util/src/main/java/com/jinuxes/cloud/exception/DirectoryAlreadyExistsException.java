package com.jinuxes.cloud.exception;

public class DirectoryAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 2625785L;

    public DirectoryAlreadyExistsException() {
    }

    public DirectoryAlreadyExistsException(String message) {
        super(message);
    }

    public DirectoryAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectoryAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public DirectoryAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
