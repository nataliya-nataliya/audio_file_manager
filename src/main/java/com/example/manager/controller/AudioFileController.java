package com.example.manager.controller;

import com.example.manager.dto.request.SavingAudioFileInfoRequestDto;
import com.example.manager.dto.request.SavingAudioFileRequestDto;
import com.example.manager.dto.response.AddingInfoAudioFileResponseDto;
import com.example.manager.dto.response.DeletingAudioFileResponseDto;
import com.example.manager.dto.response.GettingAudioFileInfoResponseDto;
import com.example.manager.dto.response.SavingAudioFileResponseDto;
import com.example.manager.exception.*;
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
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
            @ApiResponse(responseCode = "415", description = "It's not audio file"),
            @ApiResponse(responseCode = "500", description = "Audio file can not be uploaded", content = @Content)})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SavingAudioFileResponseDto> saveAudioFile(@Parameter(description = "input audio file")
                                                                    @Schema(type = "string", format = "binary")
                                                                    @RequestParam MultipartFile file) {
        SavingAudioFileResponseDto audioFileResponseDto = new SavingAudioFileResponseDto();
        try {
            SavingAudioFileRequestDto requestDto = new SavingAudioFileRequestDto(file.getOriginalFilename(), file.getBytes());
            if (!audioFileService.isAudioFile(requestDto)) {
                throw new NotFormatFileException("It's not audio file");
            }
            audioFileService.save(requestDto);
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
    public ResponseEntity<Resource> getAudioFileById(
            @Parameter(description = "id of audio file") @PathVariable long id) {
        Optional<AudioFile> audioFileOptional = audioFileService.findById(id);
        if (audioFileOptional.isPresent()) {
            Resource file = audioFileService.getFile(audioFileOptional.get());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"");
            headers.add("status", "true");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(file);
        } else {
            throw new AudioFileNotFoundException(String.format("Audio file with id %d is not found", id));
        }
    }


    @Operation(summary = "Delete audio file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File is deleted"),
            @ApiResponse(responseCode = "404", description = "Audio file is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Audio file can not be deleted", content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<DeletingAudioFileResponseDto> deleteAudioFile(
            @Parameter(description = "id of audio file") @PathVariable long id) {
        DeletingAudioFileResponseDto responseDto = new DeletingAudioFileResponseDto();
        Optional<AudioFile> audioFileOptional = audioFileService.findById(id);
        if (audioFileOptional.isPresent()) {
            try {
                audioFileService.delete(audioFileOptional.get());
            } catch (IOException e) {
                throw new NotDeletedFileException("File is not deleted");
            }
            responseDto.setStatus(true);
        } else {
            throw new AudioFileNotFoundException(String.format("Audio file with id %d is not found", id));
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @Operation(summary = "Get info of audio file by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audio file is not found"),
            @ApiResponse(responseCode = "404", description = "Audio file is not found", content = @Content)})
    @GetMapping("/{id}/info")
    public ResponseEntity<GettingAudioFileInfoResponseDto> findInfoAudioFileById(
            @Parameter(description = "id of audio file") @PathVariable long id) {
        GettingAudioFileInfoResponseDto responseDto;
        Optional<AudioFile> audioFileOptional = audioFileService.findById(id);
        if (audioFileOptional.isPresent()) {
            responseDto = audioFileService.getAudioFileInfo(audioFileOptional.get());
            responseDto.setStatus(true);
        } else {
            throw new AudioFileNotFoundException(String.format("Audio file with id %d is not found", id));
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @GetMapping("/{id}/getfile")
    public ResponseEntity<Resource> getFile(@PathVariable long id) {
        AudioFile audioFile = audioFileService.findById(id).get();
        Resource file = audioFileService.getFile(audioFile);
        if (file != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"");
            headers.add("status", "true");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(file);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("status", "false");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

}
