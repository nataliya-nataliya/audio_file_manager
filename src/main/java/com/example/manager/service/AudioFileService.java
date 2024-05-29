package com.example.manager.service;

import com.example.manager.dto.request.SavingAudioFileRequestDto;

import java.io.IOException;

public interface AudioFileService {
    void save(SavingAudioFileRequestDto savingAudioFileRequestDto) throws IOException;

}
