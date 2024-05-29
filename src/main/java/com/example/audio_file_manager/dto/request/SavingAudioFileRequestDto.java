package com.example.audio_file_manager.dto.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SavingAudioFileRequestDto {
    private String name;

    private byte[] content;
}
