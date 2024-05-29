package com.example.audio_file_manager.service;

import com.example.audio_file_manager.dto.request.SavingAudioFileRequestDto;
import com.example.audio_file_manager.model.AudioFile;
import com.example.audio_file_manager.repository.AudioFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class SimpleAudioFileService implements AudioFileService {
    private final AudioFileRepository audioFileRepository;
    @Value("${file.directory}")
    private String storageDirectory;

    @Override
    public void save(SavingAudioFileRequestDto savingAudioFileRequestDto) throws IOException {
        createDirectory();
        var path = getNewFilePath(savingAudioFileRequestDto.getName());
        writeFileBytes(path, savingAudioFileRequestDto.getContent());
        audioFileRepository.save(
                AudioFile.builder()
                        .fileName(savingAudioFileRequestDto.getName())
                        .build());
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) throws IOException {
            Files.write(Path.of(path), content);
    }

    private void createDirectory() throws IOException {
        Path directoryPath = Paths.get(storageDirectory);
        if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
        }
    }
}
