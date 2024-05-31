package com.example.manager.controller;

import com.example.manager.dto.request.SavingAudioFileInfoRequestDto;
import com.example.manager.dto.request.SavingAudioFileRequestDto;
import com.example.manager.dto.response.GettingAudioFileInfoResponseDto;
import com.example.manager.model.AudioFile;
import com.example.manager.service.AudioFileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AudioFileController.class)
public class AudioFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AudioFileService audioFileService;

    private final String fakeToken = "access_token";

    @BeforeEach
    void setup() {
    }

    @Test
    void whenSaveAudioFileThenReturns200() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.mp3", MediaType.MULTIPART_FORM_DATA_VALUE, "test data".getBytes());
        when(audioFileService.isAudioFile(Mockito.any(SavingAudioFileRequestDto.class))).thenReturn(true);

        mockMvc.perform(multipart("/files")
                        .file(file)
                        .header("token", fakeToken))
                .andExpect(status().isOk());
    }

    @Test
    void whenAddInfoAboutAudioFileThenReturns200() throws Exception {
        long fileId = 1L;
        SavingAudioFileInfoRequestDto infoRequestDto = new SavingAudioFileInfoRequestDto();
        AudioFile audioFile = new AudioFile();
        Optional<AudioFile> optionalAudioFile = Optional.of(audioFile);

        when(audioFileService.findById(fileId)).thenReturn(optionalAudioFile);
        when(audioFileService.addInfoToAudioFile(Mockito.any(AudioFile.class), Mockito.any(SavingAudioFileInfoRequestDto.class))).thenReturn(optionalAudioFile);

        mockMvc.perform(put("/files/{id}", fileId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(infoRequestDto))
                        .header("token", fakeToken))
                .andExpect(status().isOk());
    }

    @Test
    void whenGetAudioFileByIdThenReturns200() throws Exception {
        long fileId = 1L;
        AudioFile audioFile = new AudioFile();
        audioFile.setId(fileId);
        Optional<AudioFile> optionalAudioFile = Optional.of(audioFile);
        Resource resource = new ByteArrayResource("test data".getBytes());

        when(audioFileService.findById(fileId)).thenReturn(optionalAudioFile);
        when(audioFileService.getFile(audioFile)).thenReturn(resource);

        mockMvc.perform(get("/files/{id}", fileId)
                        .header("token", fakeToken))
                .andExpect(status().isOk());
    }

    @Test
    void whenDeleteAudioFileThenReturns200() throws Exception {
        long fileId = 1L;
        AudioFile audioFile = new AudioFile();
        Optional<AudioFile> optionalAudioFile = Optional.of(audioFile);

        when(audioFileService.findById(fileId)).thenReturn(optionalAudioFile);

        mockMvc.perform(delete("/files/{id}", fileId)
                        .header("token", fakeToken))
                .andExpect(status().isOk());
    }

    @Test
    void whenFindInfoAudioFileByIdThenReturns200() throws Exception {
        long fileId = 1L;
        AudioFile audioFile = new AudioFile();
        Optional<AudioFile> optionalAudioFile = Optional.of(audioFile);
        GettingAudioFileInfoResponseDto responseDto = new GettingAudioFileInfoResponseDto();
        responseDto.setStatus(true);

        when(audioFileService.findById(fileId)).thenReturn(optionalAudioFile);
        when(audioFileService.getAudioFileInfo(audioFile)).thenReturn(responseDto);

        mockMvc.perform(get("/files/{id}/info", fileId)
                        .header("token", fakeToken))
                .andExpect(status().isOk());
    }
}
