package com.example.manager.exception;

public class NotDownloadFileException extends RuntimeException {
    public NotDownloadFileException(String message) {
        super(message);
    }
}
