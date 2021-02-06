package com.hipsterheaven.music.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hipsterheaven.music.exceptions.ResourceNotFoundException;
import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.resources.TestResources;
import com.hipsterheaven.music.services.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.hipsterheaven.music.resources.TestResources.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AlbumControllerTest {

    private AlbumService albumService;
    private AlbumController underTest;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        albumService = mock(AlbumService.class);
        underTest = new AlbumController(albumService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void albumControllerRetrieveAllAlbumsShouldReturnAllAlbums() {
        when(albumService.retrieveAllAlbums()).thenReturn(List.of(TEST_ALBUM));
        Iterable<Album> albums = underTest.retrieveAllAlbums();
        assertThat(albums).contains(TEST_ALBUM);
    }

    @Test
    public void albumControllerRetrieveAllAlbumsShouldBeMapped() throws Exception {
        when(albumService.retrieveAllAlbums()).thenReturn(List.of(TEST_ALBUM));
        mockMvc.perform(get("/api/albums"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].title", equalTo(TEST_ALBUM.getTitle())));
    }

    @Test
    public void albumControllerRetrieveAlbumIsMapped() throws Exception {
        when(albumService.retrieveAlbumById(1L)).thenReturn(TEST_ALBUM);
        mockMvc.perform(get("/api/albums/1"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(TEST_ALBUM.getTitle())));
    }

    @Test
    public void albumControllerCreateAlbumIsMapped() throws Exception {
        when(albumService.save(TEST_ALBUM)).thenReturn(TEST_ALBUM);
        mockMvc.perform(post("/api/albums")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(TEST_ALBUM)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(TEST_ALBUM.getTitle())));
    }

    @Test
    public void albumControllerCanDeleteAlbum() throws Exception {
        mockMvc.perform(delete("/api/albums/1"))
               .andExpect(status().isOk());
    }

    @Test
    public void albumControllerReturn404IfAlbumToDeleteIsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Some Message")).when(albumService).delete(404L);
        mockMvc.perform(delete("/api/albums/404"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void albumControllerCanAddSongToAlbum() throws Exception {
        when(albumService.addSongToAlbum(TEST_SONG, 1L)).thenReturn(TEST_ALBUM);
        mockMvc.perform(patch("/api/albums/1/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(TEST_SONG)))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(TEST_ALBUM.getTitle())));
    }

    @Test
    public void albumControllerReturn404IfAlbumToAddSongIsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Some Message")).when(albumService).addSongToAlbum(TEST_SONG, 404L);
        mockMvc.perform(patch("/api/albums/404/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(TEST_SONG)))
               .andExpect(status().isNotFound());
    }

    @Test
    public void albumControllerCanRetrieveAlbumsListOfSongs() throws Exception {
        when(albumService.retrieveAlbumSongs(1L)).thenReturn(List.of(TEST_SONG));
        mockMvc.perform(get("/api/albums/1/songs"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].title", equalTo(TEST_SONG.getTitle())));
    }

    @Test
    public void albumControllerReturn404IfAlbumToRetrieveSongsFromIsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Some Message")).when(albumService).retrieveAlbumSongs(404L);
        mockMvc.perform(get("/api/albums/404/songs"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void albumControllerCanAddCommentToAlbum() throws Exception {
        when(albumService.addCommentToAlbum(TestResources.TEST_COMMENT, 1L)).thenReturn(TEST_ALBUM);
        mockMvc.perform(patch("/api/albums/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(TEST_COMMENT)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(TEST_ALBUM.getTitle())));
    }

    @Test
    public void albumControllerReturn404IfAlbumToAddCommentToIsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Some Message")).when(albumService).addCommentToAlbum(TEST_COMMENT, 404L);
        mockMvc.perform(patch("/api/albums/404/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(TEST_COMMENT)))
               .andExpect(status().isNotFound());
    }
}
