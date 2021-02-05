package com.hipsterheaven.music;

import com.hipsterheaven.music.exceptions.ResourceNotFoundException;
import com.hipsterheaven.music.repositories.SongRepository;
import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.resources.Song;
import com.hipsterheaven.music.services.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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
        testAlbum = new Album("Sample Album", "Sample Artist", "Testing Records", "This is a testing sample.");
        testSong = new Song("Sample Song", "4:31", 5, testAlbum);
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
        assertThat(underTest.retrieveAllSong()).contains(testSong);
    }

    @Test
    public void songServiceShouldDeleteAlbum() {
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
}
