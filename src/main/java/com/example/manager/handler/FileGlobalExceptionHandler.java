package com.example.manager.handler;

import com.example.manager.exception.*;
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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<FileNotUploadData> handlerException(NotUploadFileException exception) {
        FileNotUploadData data = FileNotUploadData.builder()
                .info(exception.getMessage()).build();
        log.error("Exception occurred with HTTP status {}: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getLocalizedMessage());
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<FileNotDownloadData> handlerException(NotDownloadFileException exception) {
        FileNotDownloadData data = FileNotDownloadData.builder()
                .info(exception.getMessage()).build();
        log.error("Exception occurred with HTTP status {}: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getLocalizedMessage());
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<AudioFileNotFoundData> handlerException(AudioFileNotFoundException exception) {
        AudioFileNotFoundData data = AudioFileNotFoundData.builder()
                .info(exception.getMessage()).build();
        log.error("Exception occurred with HTTP status {}: {}", HttpStatus.NOT_FOUND.value(),
                exception.getLocalizedMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<AudioFileNotFoundData> handlerException(NotAddInfoToFileException exception) {
        AudioFileNotFoundData data = AudioFileNotFoundData.builder()
                .info(exception.getMessage()).build();
        log.error("Exception occurred with HTTP status {}: {}", HttpStatus.CONFLICT.value(),
                exception.getLocalizedMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<FileNotFormatData> handlerException(NotFormatFileException exception) {
        FileNotFormatData data = FileNotFormatData.builder()
                .info(exception.getMessage()).build();
        log.error("Exception occurred with HTTP status {}: {}", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                exception.getLocalizedMessage());
        return new ResponseEntity<>(data, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
