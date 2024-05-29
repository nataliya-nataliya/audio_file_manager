package com.example.audio_file_manager.handler;

import com.example.audio_file_manager.exception.NotSaveFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class FileGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<FileNotSaveData> handlerException(NotSaveFileException exception) {
        FileNotSaveData data = FileNotSaveData.builder()
                .info(exception.getMessage()).build();
        log.error("Exception occurred with HTTP status {}: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getLocalizedMessage());
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
