package com.jinuxes.cloud.exception;

public class RoleAlreadyExistException extends RuntimeException{
    private static final long serialVersionUID = 15666L;

    public RoleAlreadyExistException() {
    }

    public RoleAlreadyExistException(String message) {
        super(message);
    }

    public RoleAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public RoleAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
