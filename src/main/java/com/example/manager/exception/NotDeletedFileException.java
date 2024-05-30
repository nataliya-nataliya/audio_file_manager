package com.example.manager.exception;

public class NotDeletedFileException extends RuntimeException {
    public NotDeletedFileException(String message) {
        super(message);
    }
}
