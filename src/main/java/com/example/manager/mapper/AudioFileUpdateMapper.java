package com.example.manager.mapper;


import com.example.manager.dto.request.SavingAudioFileInfoRequestDto;
import com.example.manager.model.AudioFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AudioFileUpdateMapper {

    @Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    AudioFile dtoToEntity(SavingAudioFileInfoRequestDto dto);
}
