package com.example.audio_file_manager.repository;

import com.example.audio_file_manager.model.AudioFile;
import org.springframework.data.repository.CrudRepository;

public interface AudioFileRepository extends CrudRepository<AudioFile, Long> {
}
