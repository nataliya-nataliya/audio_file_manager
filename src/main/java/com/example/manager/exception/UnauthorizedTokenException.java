package com.example.manager.exception;

public class UnauthorizedTokenException extends RuntimeException {
    public UnauthorizedTokenException() {
        super("The token provided is rejected");
    }
}
