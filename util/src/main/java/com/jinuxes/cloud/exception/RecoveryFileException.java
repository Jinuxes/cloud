package com.jinuxes.cloud.exception;

public class RecoveryFileException extends RuntimeException{
    private static final long serialVersionUID = 56858545L;

    public RecoveryFileException() {
    }

    public RecoveryFileException(String message) {
        super(message);
    }

    public RecoveryFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecoveryFileException(Throwable cause) {
        super(cause);
    }

    public RecoveryFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
