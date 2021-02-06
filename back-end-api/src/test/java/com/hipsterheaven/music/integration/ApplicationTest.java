package com.hipsterheaven.music.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.resources.Comment;
import com.hipsterheaven.music.resources.Song;
import com.hipsterheaven.music.services.AlbumService;
import com.hipsterheaven.music.services.SongService;
import com.hipsterheaven.music.services.repositories.AlbumRepository;
import com.hipsterheaven.music.services.repositories.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.hipsterheaven.music.resources.TestResources.TEST_ALBUM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {
    Album testAlbum;
    Song testSong;
    Comment testComment;
    @Autowired
    private AlbumRepository albumRepo;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongRepository songRepo;
    @Autowired
    private SongService songService;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        songRepo.deleteAll();
        albumRepo.deleteAll();
        testAlbum = new Album("Test Album", "Testy Testarosa", "Unit Test Inc", "An album for testing.", "http://placekitten.com/200/300");
        testSong = new Song("Testing the Night Away", "4:31", 5, testAlbum, "https://youtu.be/dQw4w9WgXcQ");
        testComment = new Comment("Testing is awesome!", "Testy");
        albumService.save(testAlbum);
        songService.save(testSong);
    }

    @Test
    public void shouldBeAbleToDeleteAlbumWithSongs() {
        albumService.delete(testAlbum.getId());
        assertThat(albumRepo.count()).isEqualTo(0);
        assertThat(songRepo.count()).isEqualTo(0);
    }

    @Test
    public void retrieveAllSongsShouldBeMapped() throws Exception {
        mockMvc.perform(get("/api/songs"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].title", equalTo(testSong.getTitle())));
    }

    @Test
    public void retrieveSongIsMapped() throws Exception {
        mockMvc.perform(get("/api/songs/" + testSong.getId()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(testSong.getTitle())));
    }

    @Test
    public void deleteSong() throws Exception {
        mockMvc.perform(delete("/api/songs/" + testSong.getId()))
               .andExpect(status().isOk());
    }

    @Test
    public void return404IfSongToDeleteIsNotFound() throws Exception {
        mockMvc.perform(delete("/api/songs/404"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void retrieveSongAlbum() throws Exception {
        mockMvc.perform(get("/api/songs/" + testSong.getId() + "/album"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(testAlbum.getTitle())));
    }

    @Test
    public void return404IfSongIsNotFound() throws Exception {
        mockMvc.perform(get("/api/songs/404/album"))
               .andExpect(status().isNotFound());
    }


    @Test
    public void addCommentToSong() throws Exception {
        mockMvc.perform(patch("/api/songs/" + testSong.getId() + "/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testComment)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(testSong.getTitle())));
    }

    @Test
    public void return404IfSongToAddCommentToIsNotFound() throws Exception {
        mockMvc.perform(patch("/api/songs/404/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testComment)))
               .andExpect(status().isNotFound());
    }

    @Test
    public void retrieveAllAlbumsShouldBeMapped() throws Exception {
        mockMvc.perform(get("/api/albums"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].title", equalTo(testAlbum.getTitle())));
    }

    @Test
    public void retrieveAlbumIsMapped() throws Exception {
        mockMvc.perform(get("/api/albums/" + testAlbum.getId()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(testAlbum.getTitle())));
    }

    @Test
    public void createAlbumIsMapped() throws Exception {
        mockMvc.perform(post("/api/albums")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(TEST_ALBUM)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(TEST_ALBUM.getTitle())));
    }

    @Test
    public void deleteAlbum() throws Exception {
        mockMvc.perform(delete("/api/albums/" + testAlbum.getId()))
               .andExpect(status().isOk());
    }

    @Test
    public void rturn404IfAlbumToDeleteIsNotFound() throws Exception {
        mockMvc.perform(delete("/api/albums/404"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void addSongToAlbum() throws Exception {
        mockMvc.perform(patch("/api/albums/" + testAlbum.getId() + "/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testSong)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(testAlbum.getTitle())));
    }

    @Test
    public void return404IfAlbumToAddSongIsNotFound() throws Exception {
        mockMvc.perform(patch("/api/albums/404/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testSong)))
               .andExpect(status().isNotFound());
    }

    @Test
    public void retrieveAlbumsListOfSongs() throws Exception {
        mockMvc.perform(get("/api/albums/" + testAlbum.getId() + "/songs"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].title", equalTo(testSong.getTitle())));
    }

    @Test
    public void return404IfAlbumToRetrieveSongsFromIsNotFound() throws Exception {
        mockMvc.perform(get("/api/albums/404/songs"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void addCommentToAlbum() throws Exception {
        mockMvc.perform(patch("/api/albums/" + testAlbum.getId() + "/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testComment)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.title", equalTo(testAlbum.getTitle())));
    }

    @Test
    public void return404IfAlbumToAddCommentToIsNotFound() throws Exception {
        mockMvc.perform(patch("/api/albums/404/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testComment)))
               .andExpect(status().isNotFound());
    }
}
