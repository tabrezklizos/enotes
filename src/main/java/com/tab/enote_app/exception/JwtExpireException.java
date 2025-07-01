package com.tab.enote_app.exception;

public class JwtExpireException extends RuntimeException {
    public JwtExpireException(String message) {
        super(message);
    }
}
