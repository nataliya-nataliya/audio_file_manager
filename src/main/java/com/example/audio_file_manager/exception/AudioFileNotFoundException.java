package com.example.audio_file_manager.exception;

public class AudioFileNotFoundException extends RuntimeException {
    public AudioFileNotFoundException(String message) {
        super(message);
    }
}
