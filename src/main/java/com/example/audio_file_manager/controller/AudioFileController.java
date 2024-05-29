package com.example.audio_file_manager.controller;

import com.example.audio_file_manager.dto.request.SavingAudioFileRequestDto;
import com.example.audio_file_manager.dto.response.SavingAudioFileResponseDto;
import com.example.audio_file_manager.exception.NotSaveFileException;
import com.example.audio_file_manager.service.AudioFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
public class AudioFileController {
    private final AudioFileService audioFileService;

    @PostMapping()
    public ResponseEntity<SavingAudioFileResponseDto> saveAudioFile(@RequestParam MultipartFile file) {
        SavingAudioFileResponseDto audioFileResponseDto = new SavingAudioFileResponseDto(true);
        try {
            audioFileService.save(new SavingAudioFileRequestDto(file.getOriginalFilename(), file.getBytes()));
        } catch (IOException ex) {
            throw new NotSaveFileException("File not saved");
        }
        return new ResponseEntity<>(audioFileResponseDto, HttpStatus.OK);
    }

}
