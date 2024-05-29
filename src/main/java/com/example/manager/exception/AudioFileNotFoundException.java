package com.example.manager.exception;

public class AudioFileNotFoundException extends RuntimeException {
    public AudioFileNotFoundException(String message) {
        super(message);
    }
}
