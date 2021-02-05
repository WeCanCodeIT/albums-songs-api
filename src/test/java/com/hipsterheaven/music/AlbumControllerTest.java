package com.hipsterheaven.music;

import com.hipsterheaven.music.controllers.AlbumController;
import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.services.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.hipsterheaven.music.resources.TestResources.TEST_ALBUM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AlbumControllerTest {

    private AlbumService testService;
    private AlbumController underTest;
    private MockMvc mockMvc;


    @BeforeEach
    public void setUp() {
        testService = mock(AlbumService.class);
        underTest = new AlbumController(testService);
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
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
        mockMvc.perform(post("/api/albums")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(albumJson))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(TEST_ALBUM.getTitle())));
    }

}
