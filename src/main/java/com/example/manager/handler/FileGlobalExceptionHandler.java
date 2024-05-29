package com.example.manager.handler;

import com.example.manager.exception.NotSaveFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class FileGlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FileNotSaveData> handlerException(NotSaveFileException exception) {
        FileNotSaveData data = FileNotSaveData.builder()
                .info(exception.getMessage()).build();
        log.error("Exception occurred with HTTP status {}: {}", HttpStatus.BAD_REQUEST.value(),
                exception.getLocalizedMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
