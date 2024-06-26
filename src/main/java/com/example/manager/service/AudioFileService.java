package com.example.manager.service;

import com.example.manager.dto.request.SavingAudioFileInfoRequestDto;
import com.example.manager.dto.request.SavingAudioFileRequestDto;
import com.example.manager.dto.response.GettingAudioFileInfoResponseDto;
import com.example.manager.model.AudioFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Optional;

public interface AudioFileService {
    void save(SavingAudioFileRequestDto savingAudioFileRequestDto) throws IOException;

    Optional<AudioFile> addInfoToAudioFile(AudioFile audioFile, SavingAudioFileInfoRequestDto infoRequestDto);

    Optional<AudioFile> findById(long id);

    String getNewFilePath(String sourceName);

    boolean isAudioFile(SavingAudioFileRequestDto savingAudioFileRequestDto);

    void delete(AudioFile audioFile) throws IOException;

    GettingAudioFileInfoResponseDto getAudioFileInfo(AudioFile audioFile);

    Resource getFile(AudioFile audioFile);
}
