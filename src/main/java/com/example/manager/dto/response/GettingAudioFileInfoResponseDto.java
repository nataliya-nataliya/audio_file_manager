package com.example.manager.dto.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GettingAudioFileInfoResponseDto {

    private boolean status;

    private long id;

    private String date;

    private String fileName;

    private int duration;
}
