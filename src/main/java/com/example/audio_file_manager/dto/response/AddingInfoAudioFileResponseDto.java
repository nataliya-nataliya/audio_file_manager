package com.example.audio_file_manager.dto.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddingInfoAudioFileResponseDto {
    private boolean status;
    private long id;
}
