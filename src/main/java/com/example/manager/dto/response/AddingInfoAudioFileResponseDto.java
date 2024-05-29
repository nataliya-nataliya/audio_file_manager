package com.example.manager.dto.response;

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
