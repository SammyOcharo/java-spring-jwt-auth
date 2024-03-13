package com.samdev.jwt_auth.Exceptions;

public class OtpMissMatch extends RuntimeException{
    public OtpMissMatch(String message) {
        super(message);
    }
}
