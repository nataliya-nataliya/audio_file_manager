package com.example.manager.repository;

import com.example.manager.model.AudioFile;
import org.springframework.data.repository.CrudRepository;

public interface AudioFileRepository extends CrudRepository<AudioFile, Long> {
}
