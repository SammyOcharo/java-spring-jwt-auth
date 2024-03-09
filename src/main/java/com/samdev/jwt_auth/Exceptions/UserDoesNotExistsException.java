package com.samdev.jwt_auth.Exceptions;

public class UserDoesNotExistsException extends RuntimeException{
    public UserDoesNotExistsException(String message) {
        super(message);
    }
}
