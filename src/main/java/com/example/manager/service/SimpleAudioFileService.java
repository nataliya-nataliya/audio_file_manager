package com.example.manager.service;

import com.example.manager.dto.request.SavingAudioFileInfoRequestDto;
import com.example.manager.dto.request.SavingAudioFileRequestDto;
import com.example.manager.dto.response.GettingAudioFileInfoResponseDto;
import com.example.manager.mapper.AudioFileUpdateMapper;
import com.example.manager.model.AudioFile;
import com.example.manager.repository.AudioFileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleAudioFileService implements AudioFileService {
    private final AudioFileRepository audioFileRepository;
    @Value("${file.directory}")
    private String storageDirectory;
    private final AudioFileUpdateMapper updateMapper;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


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
        LocalDateTime date = LocalDateTime.from(updateMapper.dtoToEntity(infoRequestDto).getDate());
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

    public boolean isAudioFile(SavingAudioFileRequestDto savingAudioFileRequestDto) {
        Tika tika = new Tika();
        byte[] fileContent = savingAudioFileRequestDto.getContent();
        String mimeType = tika.detect(fileContent);
        return mimeType.startsWith("audio/");
    }

    @Override
    public void delete(AudioFile audioFile) throws IOException {
        Path filePath = Paths.get(storageDirectory + java.io.File.separator + audioFile.getFileName());
        Files.deleteIfExists(filePath);
        audioFileRepository.deleteById(audioFile.getId());
    }

    @Override
    public GettingAudioFileInfoResponseDto getAudioFileInfo(AudioFile audioFile) {
        LocalDateTime dateTime = audioFile.getDate();
        String date = (dateTime != null) ? dateTime.format(formatter) : null;
        return GettingAudioFileInfoResponseDto.builder()
                .fileName(audioFile.getFileName())
                .duration(audioFile.getDuration())
                .id(audioFile.getId())
                .date(date)
                .build();
    }

    public Resource getFile(AudioFile audioFile) {
        Path filePath = Paths.get(storageDirectory, audioFile.getFileName());
        Resource fileResource = null;
        try {
            fileResource = new org.springframework.core.io.UrlResource(filePath.toUri());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileResource;
    }
}
