package com.example.manager.dto.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GettingAudioFileResponseDto {

    private boolean status;

    private String link;
}
