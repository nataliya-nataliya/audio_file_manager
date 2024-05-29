package com.example.manager.controller;

import com.example.manager.dto.request.SavingAudioFileInfoRequestDto;
import com.example.manager.dto.request.SavingAudioFileRequestDto;
import com.example.manager.dto.response.AddingInfoAudioFileResponseDto;
import com.example.manager.dto.response.GettingAudioFileResponseDto;
import com.example.manager.dto.response.SavingAudioFileResponseDto;
import com.example.manager.exception.AudioFileNotFoundException;
import com.example.manager.exception.NotAddInfoToFileException;
import com.example.manager.exception.NotUploadFileException;
import com.example.manager.model.AudioFile;
import com.example.manager.service.AudioFileService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
public class AudioFileController {
    private final AudioFileService audioFileService;


    @Operation(summary = "Save audio file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audio file is saved"),
            @ApiResponse(responseCode = "500", description = "Audio file can not be uploaded", content = @Content)})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SavingAudioFileResponseDto> saveAudioFile(@Parameter(description = "input audio file")
                                                                    @Schema(type = "string", format = "binary")
                                                                    @RequestParam MultipartFile file) {
        SavingAudioFileResponseDto audioFileResponseDto = new SavingAudioFileResponseDto();
        try {
            audioFileService.save(new SavingAudioFileRequestDto(file.getOriginalFilename(), file.getBytes()));
            audioFileResponseDto.setStatus(true);
        } catch (IOException ex) {
            throw new NotUploadFileException("File not saved");
        }
        return new ResponseEntity<>(audioFileResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "Add info about audio file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Info is added"),
            @ApiResponse(responseCode = "404", description = "Audio file is not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Info about audio file is not added", content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<AddingInfoAudioFileResponseDto> addInfoAboutAudioFile(
            @Parameter(description = "id of audio file") @PathVariable long id,
            @RequestBody SavingAudioFileInfoRequestDto infoRequestDto) {
        AddingInfoAudioFileResponseDto responseDto = new AddingInfoAudioFileResponseDto();
        Optional<AudioFile> audioFileOptional = audioFileService.findById(id);
        if (audioFileOptional.isPresent()) {
            if (audioFileService.addInfoToAudioFile(audioFileOptional.get(), infoRequestDto).isPresent()) {
                responseDto.setId(id);
                responseDto.setStatus(true);
            } else {
                throw new NotAddInfoToFileException("Info is not added");
            }
        } else {
            throw new AudioFileNotFoundException(String.format("Audio file with id %d is not found", id));
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "Find audio file by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audio file is not found"),
            @ApiResponse(responseCode = "404", description = "Audio file is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Audio file can not be downloaded", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<GettingAudioFileResponseDto> findAudioFileById(
            @Parameter(description = "id of audio file") @PathVariable long id) {
        GettingAudioFileResponseDto responseDto = new GettingAudioFileResponseDto();
        Optional<AudioFile> audioFileOptional = audioFileService.findById(id);
        if (audioFileOptional.isPresent()) {
            audioFileService.getNewFilePath(audioFileOptional.get().getFileName());
            responseDto.setStatus(true);
            responseDto.setLink(audioFileService.getNewFilePath(audioFileOptional.get().getFileName()));
        } else {
            throw new AudioFileNotFoundException(String.format("Audio file with id %d is not found", id));
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
