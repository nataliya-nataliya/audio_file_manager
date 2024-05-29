package com.example.audio_file_manager.service;

import com.example.audio_file_manager.dto.request.SavingAudioFileInfoRequestDto;
import com.example.audio_file_manager.dto.request.SavingAudioFileRequestDto;
import com.example.audio_file_manager.model.AudioFile;

import java.io.IOException;
import java.util.Optional;

public interface AudioFileService {
    void save(SavingAudioFileRequestDto savingAudioFileRequestDto) throws IOException;

    Optional<AudioFile> addInfoToAudioFile(AudioFile audioFile, SavingAudioFileInfoRequestDto infoRequestDto);
    Optional<AudioFile> findById(long id);

}
