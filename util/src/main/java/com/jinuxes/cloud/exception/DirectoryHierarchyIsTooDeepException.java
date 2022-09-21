package com.jinuxes.cloud.exception;

public class DirectoryHierarchyIsTooDeepException extends RuntimeException{
    private static final long serialVersionUID = 42424445L;

    public DirectoryHierarchyIsTooDeepException() {
    }

    public DirectoryHierarchyIsTooDeepException(String message) {
        super(message);
    }

    public DirectoryHierarchyIsTooDeepException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectoryHierarchyIsTooDeepException(Throwable cause) {
        super(cause);
    }

    public DirectoryHierarchyIsTooDeepException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
