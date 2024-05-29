package com.example.manager.controller;

import com.example.manager.dto.request.SavingAudioFileInfoRequestDto;
import com.example.manager.dto.request.SavingAudioFileRequestDto;
import com.example.manager.dto.response.AddingInfoAudioFileResponseDto;
import com.example.manager.dto.response.SavingAudioFileResponseDto;
import com.example.manager.exception.AudioFileNotFoundException;
import com.example.manager.exception.NotAddInfoToFileException;
import com.example.manager.exception.NotSaveFileException;
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
            @ApiResponse(responseCode = "400", description = "Audio file is not saved", content = @Content)})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SavingAudioFileResponseDto> saveAudioFile(@Parameter(description = "input audio file")
                                                                    @Schema(type = "string", format = "binary")
                                                                    @RequestParam MultipartFile file) {
        SavingAudioFileResponseDto audioFileResponseDto = new SavingAudioFileResponseDto();
        try {
            audioFileService.save(new SavingAudioFileRequestDto(file.getOriginalFilename(), file.getBytes()));
            audioFileResponseDto.setStatus(true);
        } catch (IOException ex) {
            throw new NotSaveFileException("File not saved");
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
            @PathVariable String id,
            @RequestBody SavingAudioFileInfoRequestDto infoRequestDto) {
        AddingInfoAudioFileResponseDto responseDto = new AddingInfoAudioFileResponseDto();
        long idAudioFile = Long.parseLong(id);
        Optional<AudioFile> audioFileOptional = audioFileService.findById(idAudioFile);
        if (audioFileOptional.isPresent()) {
            if (audioFileService.addInfoToAudioFile(audioFileOptional.get(), infoRequestDto).isPresent()) {
                responseDto.setId(idAudioFile);
                responseDto.setStatus(true);
            } else {
                throw new NotAddInfoToFileException("Info is not added");
            }
        } else {
            throw new AudioFileNotFoundException(String.format("Audio file with id %d is not found", idAudioFile));
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
