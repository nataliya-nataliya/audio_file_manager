package com.example.audio_file_manager.dto.request;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SavingAudioFileInfoRequestDto {

    String date;

    private String fileName;

    private int duration;
}
