package com.example.audio_file_manager.mapper;

import com.example.audio_file_manager.dto.request.SavingAudioFileInfoRequestDto;
import com.example.audio_file_manager.model.AudioFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AudioFileUpdateMapper {

    @Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd")
    AudioFile dtoToEntity(SavingAudioFileInfoRequestDto dto);
}
