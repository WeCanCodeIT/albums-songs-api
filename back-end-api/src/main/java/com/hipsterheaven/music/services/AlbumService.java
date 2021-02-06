package com.hipsterheaven.music.services;

import com.hipsterheaven.music.exceptions.ResourceNotFoundException;
import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.resources.Comment;
import com.hipsterheaven.music.resources.Song;
import com.hipsterheaven.music.services.repositories.AlbumRepository;
import com.hipsterheaven.music.services.repositories.SongRepository;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
    private final AlbumRepository albumRepo;
    private final SongRepository songRepo;

    public AlbumService(AlbumRepository albumRepo, SongRepository songRepo) {
        this.albumRepo = albumRepo;
        this.songRepo = songRepo;
    }


    public Album save(Album album) {
        return albumRepo.save(album);
    }

    public Iterable<Album> retrieveAllAlbums() {
        return albumRepo.findAll();
    }


    public void delete(Long id) {
        if (!albumRepo.existsById(id)) {
            throw new ResourceNotFoundException("Requested resource " + id + " not found");
        }
        albumRepo.deleteById(id);

    }

    public Album retrieveAlbumById(Long id) {
        try {
            return albumRepo.findById(id).get();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Requested resource " + id + " not found.", e);
        }
    }

    public Album addSongToAlbum(Song song, Long albumId) {
        Album albumToModify = retrieveAlbumById(albumId);
        song.addAlbum(albumToModify);
        songRepo.save(song);
        return retrieveAlbumById(albumId);
    }

    public Iterable<Song> retrieveAlbumSongs(Long albumId) {
        return retrieveAlbumById(albumId).getSongs();
    }

    public Album addCommentToAlbum(Comment comment, Long albumId) {
        Album album = retrieveAlbumById(albumId);
        album.addComment(comment);
        return albumRepo.save(album);
    }
}
