package com.example.audio_file_manager.controller;

import com.example.audio_file_manager.dto.request.SavingAudioFileRequestDto;
import com.example.audio_file_manager.dto.response.SavingAudioFileResponseDto;
import com.example.audio_file_manager.exception.NotSaveFileException;
import com.example.audio_file_manager.service.AudioFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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


    @Operation(summary = "Save audio file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audio file is saved"),
            @ApiResponse(responseCode = "400", description = "Audio file is not saved", content = @Content)})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SavingAudioFileResponseDto> saveAudioFile(@Parameter(description = "input audio file")
                                                                    @Schema(type = "string", format = "binary")
                                                                    @RequestParam MultipartFile file) {
        SavingAudioFileResponseDto audioFileResponseDto = new SavingAudioFileResponseDto(true);
        try {
            audioFileService.save(new SavingAudioFileRequestDto(file.getOriginalFilename(), file.getBytes()));
        } catch (IOException ex) {
            throw new NotSaveFileException("File not saved");
        }
        return new ResponseEntity<>(audioFileResponseDto, HttpStatus.OK);
    }

}
