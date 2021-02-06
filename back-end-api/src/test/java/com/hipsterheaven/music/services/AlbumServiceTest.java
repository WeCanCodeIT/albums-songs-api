package com.hipsterheaven.music.services;

import com.hipsterheaven.music.exceptions.ResourceNotFoundException;
import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.resources.Comment;
import com.hipsterheaven.music.services.repositories.AlbumRepository;
import com.hipsterheaven.music.services.repositories.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.hipsterheaven.music.resources.TestResources.TEST_ALBUM;
import static com.hipsterheaven.music.resources.TestResources.TEST_SONG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class AlbumServiceTest {

    private AlbumRepository albumRepo;

    private AlbumService underTest;
    private SongRepository songRepo;

    @BeforeEach
    void setUp() {
        albumRepo = mock(AlbumRepository.class);
        songRepo = mock(SongRepository.class);
        underTest = new AlbumService(albumRepo, songRepo);
    }

    @Test
    public void albumServiceShouldSaveAlbumsToCrudRepository() {
        underTest.save(TEST_ALBUM);
        verify(albumRepo).save(TEST_ALBUM);
    }

    @Test
    public void albumServiceShouldRetrieveAllAlbums() {
        underTest.retrieveAllAlbums();
        verify(albumRepo).findAll();
    }

    @Test
    public void albumServiceShouldDeleteAlbum() {
        when(albumRepo.existsById(TEST_ALBUM.getId())).thenReturn(true);
        underTest.delete(TEST_ALBUM.getId());
        verify(albumRepo).deleteById(TEST_ALBUM.getId());
    }

    @Test
    public void albumServiceShouldThrowExceptionIfResourceNotFoundWhileDeleting() {
        assertThrows(ResourceNotFoundException.class, () -> underTest.delete(404L));
    }

    @Test
    public void albumServiceShouldRetrieveAlbumById() {
        when(albumRepo.findById(TEST_ALBUM.getId())).thenReturn(Optional.of(TEST_ALBUM));
        underTest.retrieveAlbumById(TEST_ALBUM.getId());
        verify(albumRepo).findById(TEST_ALBUM.getId());
    }

    @Test
    public void albumServiceShouldThrowExceptionIfResourceNotFound() {
        when(albumRepo.findById(404L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> underTest.retrieveAlbumById(404L));
    }

    @Test
    public void resourceNotFoundExceptionHasProperMessage() {
        when(albumRepo.findById(404L)).thenReturn(Optional.empty());
        try {
            underTest.retrieveAlbumById(404L);
            fail();
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Requested resource 404 not found.");
        }
    }

    @Test
    public void albumServiceShouldAddSongToAlbumResource() {
        when(albumRepo.findById(TEST_ALBUM.getId())).thenReturn(Optional.of(TEST_ALBUM));
        Album modifiedAlbum = underTest.addSongToAlbum(TEST_SONG, TEST_ALBUM.getId());
        verify(songRepo).save(TEST_SONG);
        assertThat(modifiedAlbum).isEqualTo(TEST_ALBUM);
    }


    @Test
    public void albumServiceShouldRetrieveAllSongsFromAlbum() {
        Album mockAlbum = mock(Album.class);
        when(mockAlbum.getSongs()).thenReturn(List.of(TEST_SONG));
        when(albumRepo.findById(1L)).thenReturn(Optional.of(mockAlbum));
        assertThat(underTest.retrieveAlbumSongs(1L)).contains(TEST_SONG);
    }

    @Test
    public void albumServiceShouldAddCommentToAlbum() {
        Album mockAlbum = mock(Album.class);
        when(albumRepo.findById(1L)).thenReturn(Optional.of(mockAlbum));
        when(albumRepo.save(mockAlbum)).thenReturn(mockAlbum);
        Comment testComment = new Comment("This album is testy!", "Tester Teston");
        assertThat(underTest.addCommentToAlbum(testComment, 1L)).isEqualTo(mockAlbum);
        verify(mockAlbum).addComment(testComment);
    }

}
