package com.jinuxes.cloud.exception;

public class FileSizeExceedsMaxUploadLimitException extends RuntimeException{
    private static final long serialVersionUID = 8548245L;

    public FileSizeExceedsMaxUploadLimitException() {
    }

    public FileSizeExceedsMaxUploadLimitException(String message) {
        super(message);
    }

    public FileSizeExceedsMaxUploadLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSizeExceedsMaxUploadLimitException(Throwable cause) {
        super(cause);
    }

    public FileSizeExceedsMaxUploadLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
