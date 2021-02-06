package com.hipsterheaven.music.services;

import com.hipsterheaven.music.exceptions.ResourceNotFoundException;
import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.resources.Song;
import com.hipsterheaven.music.services.repositories.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.hipsterheaven.music.resources.TestResources.TEST_ALBUM;
import static com.hipsterheaven.music.resources.TestResources.TEST_COMMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class SongServiceTest {

    private Album testAlbum;
    private Song testSong;
    private SongRepository testSongRepo;
    private SongService underTest;

    @BeforeEach
    void setUp() {
        testAlbum = new Album("Sample Album", "Sample Artist", "Testing Records", "This is a testing sample.", "http://placekitten.com/200/300");
        testSong = new Song("Sample Song", "4:31", 5, testAlbum, "https://youtu.be/dQw4w9WgXcQ");
        testSongRepo = mock(SongRepository.class);
        underTest = new SongService(testSongRepo);
    }

    @Test
    public void songServiceShouldSaveSong() {
        underTest.save(testSong);
        verify(testSongRepo).save(testSong);
    }

    @Test
    public void songServiceShouldRetrieveAllSongs() {
        when(testSongRepo.findAll()).thenReturn(List.of(testSong));
        assertThat(underTest.retrieveAllSongs()).contains(testSong);
    }

    @Test
    public void songServiceShouldDeleteAlbum() {
        when(testSongRepo.existsById(testSong.getId())).thenReturn(true);
        underTest.delete(testSong.getId());
        verify(testSongRepo).deleteById(testSong.getId());
    }

    @Test
    public void songServiceShouldRetrieveAlbumById() {
        when(testSongRepo.findById(testSong.getId())).thenReturn(Optional.of(testSong));
        assertThat(underTest.retrieveSongById(testSong.getId())).isEqualTo(testSong);
    }

    @Test
    public void songServiceShouldThrowExceptionIfResourceNotFound() {
        when(testSongRepo.findById(404L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> underTest.retrieveSongById(404L));
    }

    @Test
    public void resourceNotFoundExceptionHasProperMessage() {
        when(testSongRepo.findById(404L)).thenReturn(Optional.empty());
        try {
            underTest.retrieveSongById(404L);
            fail();
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Requested resource 404 not found.");
        }
    }

    @Test
    public void songServiceShouldRetrieveSongAlbum() {
        Song mockSong = mock(Song.class);
        when(testSongRepo.findById(1L)).thenReturn(Optional.of(mockSong));
        when(mockSong.getAlbum()).thenReturn(TEST_ALBUM);
        assertThat(underTest.retrieveSongAlbum(1L)).isEqualTo(TEST_ALBUM);
    }

    @Test
    public void songServiceShouldThrowExceptionIfResourceNotFoundForSongAlbum() {
        when(testSongRepo.findById(404L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> underTest.retrieveSongAlbum(404L));
    }

    @Test
    public void songServiceShouldAddCommentToSong() {
        Song mockSong = mock(Song.class);
        when(testSongRepo.findById(1L)).thenReturn(Optional.of(mockSong));
        when(testSongRepo.save(mockSong)).thenReturn(mockSong);
        assertThat(underTest.addCommentToSong(TEST_COMMENT, 1L)).isEqualTo(mockSong);
    }

    @Test
    public void songServiceShouldThrowExceptionIfResourceNotFoundWhenAddingComment() {
        when(testSongRepo.findById(404L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> underTest.addCommentToSong(TEST_COMMENT, 404L));
    }
}
