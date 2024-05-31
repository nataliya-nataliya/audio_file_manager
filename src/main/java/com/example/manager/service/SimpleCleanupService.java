package com.example.manager.service;

import com.example.manager.exception.RandomException;
import com.example.manager.model.AudioFile;
import com.example.manager.repository.AudioFileRepository;
import com.example.manager.util.RandomUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimpleCleanupService implements CleanupService {
    private final AudioFileRepository audioFileRepository;
    @Value("${max.random.number}")
    private int maxRandomNumber;

    @Value("${cleanup.threshold.days}")
    int cleanupThresholdDays;


    @Override
    @Scheduled(fixedDelay = 3600000)
    public void cleanOldInfo() {
        log.info("Scheduled background process started");
        try {
            LocalDateTime thresholdDate = LocalDateTime.now().minusDays(cleanupThresholdDays);
            log.debug("Calculated threshold date: {}", thresholdDate);
            List<AudioFile> oldFiles = audioFileRepository.findByDateBefore(thresholdDate);
            log.info("Found {} files older than {}", oldFiles.size(), thresholdDate);
            int randomNumber = RandomUtils.getRandomNumber(maxRandomNumber);
            log.debug("Generated random number: {}", randomNumber);
            if (randomNumber == 0) {
                throw new RandomException("Mock exception");
            }
            audioFileRepository.deleteAll(oldFiles);
            log.info("Deleted {} old files.", oldFiles.size());
        } catch (RandomException ex) {
            log.error("Here mock exception");
        }
        log.info("Scheduled background process finished");
    }
}
