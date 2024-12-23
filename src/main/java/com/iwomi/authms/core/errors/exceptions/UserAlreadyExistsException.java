package com.iwomi.authms.core.errors.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    private static final long serialVerisionUID = 1;
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
