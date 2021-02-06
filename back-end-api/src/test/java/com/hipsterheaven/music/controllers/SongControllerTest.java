package com.hipsterheaven.music.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hipsterheaven.music.exceptions.ResourceNotFoundException;
import com.hipsterheaven.music.services.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.hipsterheaven.music.resources.TestResources.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SongControllerTest {

    private SongService songService;
    private SongController underTest;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        songService = mock(SongService.class);
        underTest = new SongController(songService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void songControllerRetrieveAllSongsShouldBeMapped() throws Exception {
        when(songService.retrieveAllSongs()).thenReturn(List.of(TEST_SONG));
        mockMvc.perform(get("/api/songs"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].title", equalTo(TEST_SONG.getTitle())));
    }

    @Test
    public void songControllerRetrieveSongIsMapped() throws Exception {
        when(songService.retrieveSongById(1L)).thenReturn(TEST_SONG);
        mockMvc.perform(get("/api/songs/1"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(TEST_SONG.getTitle())));
    }

    @Test
    public void songControllerCanDeleteSong() throws Exception {
        mockMvc.perform(delete("/api/songs/1"))
               .andExpect(status().isOk());
    }

    @Test
    public void songControllerReturn404IfSongToDeleteIsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Some Message")).when(songService).delete(404L);
        mockMvc.perform(delete("/api/songs/404"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void songControllerReturnsSongAlbum() throws Exception {
        when(songService.retrieveSongAlbum(1L)).thenReturn(TEST_ALBUM);
        mockMvc.perform(get("/api/songs/1/album"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(TEST_ALBUM.getTitle())));
    }

    @Test
    public void songControllerReturn404IfSongIsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Some Message")).when(songService).retrieveSongAlbum(404L);
        mockMvc.perform(get("/api/songs/404/album"))
               .andExpect(status().isNotFound());
    }


    @Test
    public void songControllerCanAddCommentToSong() throws Exception {
        when(songService.addCommentToSong(TEST_COMMENT, 1L)).thenReturn(TEST_SONG);
        mockMvc.perform(patch("/api/songs/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(TEST_COMMENT)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(TEST_SONG.getTitle())));
    }

    @Test
    public void songControllerReturn404IfSongToAddCommentToIsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Some Message")).when(songService).addCommentToSong(TEST_COMMENT, 404L);
        mockMvc.perform(patch("/api/songs/404/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(TEST_COMMENT)))
               .andExpect(status().isNotFound());
    }

}
