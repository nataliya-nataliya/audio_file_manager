package com.example.manager.dto.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//TODO Добавить валидаторы
public class SavingAudioFileRequestDto {
    private String name;

    private byte[] content;
}
