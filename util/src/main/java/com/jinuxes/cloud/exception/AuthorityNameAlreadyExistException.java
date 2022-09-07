package com.jinuxes.cloud.exception;

public class AuthorityNameAlreadyExistException extends RuntimeException{
    private static final long serialVersionUID = 15687L;

    public AuthorityNameAlreadyExistException() {
    }

    public AuthorityNameAlreadyExistException(String message) {
        super(message);
    }

    public AuthorityNameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorityNameAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public AuthorityNameAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
