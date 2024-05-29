package com.example.manager.service;

import com.example.manager.dto.request.SavingAudioFileInfoRequestDto;
import com.example.manager.dto.request.SavingAudioFileRequestDto;
import com.example.manager.mapper.AudioFileUpdateMapper;
import com.example.manager.model.AudioFile;
import com.example.manager.repository.AudioFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleAudioFileService implements AudioFileService {
    private final AudioFileRepository audioFileRepository;
    @Value("${file.directory}")
    private String storageDirectory;
    private final AudioFileUpdateMapper mapper;

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

    @Override
    public Optional<AudioFile> addInfoToAudioFile(AudioFile audioFile, SavingAudioFileInfoRequestDto infoRequestDto) {
        LocalDateTime date = LocalDateTime.from(mapper.dtoToEntity(infoRequestDto).getDate());
        audioFile.setFileName(infoRequestDto.getFileName());
        audioFile.setDate(date);
        audioFile.setDuration(infoRequestDto.getDuration());
        return Optional.of(audioFileRepository.save(audioFile));
    }

    @Override
    public Optional<AudioFile> findById(long id) {
        return audioFileRepository.findById(id);
    }


    public String getNewFilePath(String sourceName) {
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
