package com.hipsterheaven.music;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hipsterheaven.music.controllers.AlbumController;
import com.hipsterheaven.music.exceptions.ResourceNotFoundException;
import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.services.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.hipsterheaven.music.resources.TestResources.TEST_ALBUM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AlbumControllerTest {

    private AlbumService testService;
    private AlbumController underTest;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        testService = mock(AlbumService.class);
        underTest = new AlbumController(testService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void albumControllerRetrieveAllAlbumsShouldReturnAllAlbums() {
        when(testService.retrieveAllAlbums()).thenReturn(List.of(TEST_ALBUM));
        Iterable<Album> albums = underTest.retrieveAllAlbums();
        assertThat(albums).contains(TEST_ALBUM);
    }

    @Test
    public void albumControllerRetrieveAllAlbumsShouldBeMapped() throws Exception {
        when(testService.retrieveAllAlbums()).thenReturn(List.of(TEST_ALBUM));
        mockMvc.perform(get("/api/albums"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", equalTo(TEST_ALBUM.getTitle())));
    }

    @Test
    public void albumControllerRetrieveAlbumIsMapped() throws Exception {
        when(testService.retrieveAlbumById(1L)).thenReturn(TEST_ALBUM);
        mockMvc.perform(get("/api/albums/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", equalTo(TEST_ALBUM.getTitle())));
    }

    @Test
    public void albumControllerCreateAlbumIsMapped() throws Exception {
        when(testService.save(TEST_ALBUM)).thenReturn(TEST_ALBUM);
        mockMvc.perform(post("/api/albums")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(TEST_ALBUM)))
                .andDo(print())
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
        doThrow(new ResourceNotFoundException("Some Message")).when(testService).delete(404L);
        mockMvc.perform(delete("/api/albums/404"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void albumControllerCanAddSongToAlbum(){

    }

}
