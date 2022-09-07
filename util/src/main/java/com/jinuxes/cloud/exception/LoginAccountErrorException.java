package com.jinuxes.cloud.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginAccountErrorException extends AuthenticationException {
    private static final long serialVersionUID = 2685745L;

    public LoginAccountErrorException(String msg, Throwable t) {
        super(msg, t);
    }

    public LoginAccountErrorException(String msg) {
        super(msg);
    }
}
