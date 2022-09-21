package com.jinuxes.cloud.exception;

public class ParenPathDoesNotExistException extends RuntimeException{
    private static final long serialVersionUID = 178525L;

    public ParenPathDoesNotExistException() {
    }

    public ParenPathDoesNotExistException(String message) {
        super(message);
    }

    public ParenPathDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParenPathDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public ParenPathDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
