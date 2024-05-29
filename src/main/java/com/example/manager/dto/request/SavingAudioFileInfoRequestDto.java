package com.example.manager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SavingAudioFileInfoRequestDto {
    @Schema(description = "date of audio file", example = "2000-01-31T14:30:00")
    String date;

    @Schema(description = "file name of audio file")
    private String fileName;

    @Schema(description = "duration of audio file")
    private int duration;
}
