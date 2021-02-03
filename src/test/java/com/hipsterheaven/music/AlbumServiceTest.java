package com.hipsterheaven.music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class AlbumServiceTest {

    private AlbumRepository testAlbumRepo;
    private Album testAlbum;
    private AlbumService underTest;

    @BeforeEach
    void setUp() {
        testAlbumRepo = mock(AlbumRepository.class);
        testAlbum = new Album("Testing in Test Town", "Testy McTesterson", "Exam Records",
                "Testing the night away.");
        underTest = new AlbumService(testAlbumRepo);
    }

    @Test
    public void albumServiceShouldSaveAlbumsToCrudRepository() {
        underTest.save(testAlbum);
        verify(testAlbumRepo).save(testAlbum);
    }

    @Test
    public void albumServiceShouldRetrieveAllAlbums() {
        underTest.retrieveAllAlbums();
        verify(testAlbumRepo).findAll();
    }

    @Test
    public void albumServiceShouldDeleteAlbum() {
        underTest.delete(testAlbum.getId());
        verify(testAlbumRepo).deleteById(testAlbum.getId());
    }

    @Test
    public void albumServiceShouldRetrieveAlbumById() {
        when(testAlbumRepo.findById(testAlbum.getId())).thenReturn(Optional.of(testAlbum));
        underTest.retrieveAlbumById(testAlbum.getId());
        verify(testAlbumRepo).findById(testAlbum.getId());
    }

    @Test
    public void albumServiceShouldThrowExceptionIfResourceNotFound() {
        when(testAlbumRepo.findById(404L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> underTest.retrieveAlbumById(404L));
    }

    @Test
    public void resourceNotFoundExceptionHasProperMessage() {
        when(testAlbumRepo.findById(404L)).thenReturn(Optional.empty());
        try {
            underTest.retrieveAlbumById(404L);
            fail();
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Requested resource 404 not found.");
        }
    }

}
