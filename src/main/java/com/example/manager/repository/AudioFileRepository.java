package com.example.manager.repository;

import com.example.manager.model.AudioFile;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AudioFileRepository extends CrudRepository<AudioFile, Long> {
    List<AudioFile> findByDateBefore(LocalDateTime date);

}
