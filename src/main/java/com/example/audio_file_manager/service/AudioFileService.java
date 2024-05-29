package com.example.audio_file_manager.service;

import com.example.audio_file_manager.dto.request.SavingAudioFileRequestDto;

import java.io.IOException;

public interface AudioFileService {
    void save(SavingAudioFileRequestDto savingAudioFileRequestDto) throws IOException;

}
