package com.hipsterheaven.music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

        underTest.retrieveAlbumById(testAlbum.getId());
        verify(testAlbumRepo).findById(testAlbum.getId());
    }

}
