package com.example.manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "info")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AudioFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private final LocalDateTime date = LocalDateTime.now();

    @Column(name = "file_name", length = 64)
    private String fileName;

    private int duration;
}
