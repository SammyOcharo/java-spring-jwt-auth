package com.samdev.jwt_auth.Exceptions;

public class OtpMissmatch extends RuntimeException{
    public OtpMissmatch(String message) {
        super(message);
    }
}
