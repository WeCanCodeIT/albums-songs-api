package com.hipsterheaven.music.services;

import com.hipsterheaven.music.exceptions.ResourceNotFoundException;
import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.resources.Comment;
import com.hipsterheaven.music.resources.Song;
import com.hipsterheaven.music.services.repositories.SongRepository;
import org.springframework.stereotype.Service;

@Service
public class SongService {
    private final SongRepository songRepo;

    public SongService(SongRepository songRepo) {
        this.songRepo = songRepo;
    }

    public void save(Song song) {
        songRepo.save(song);
    }

    public Iterable<Song> retrieveAllSongs() {
        return songRepo.findAll();
    }


    public void delete(Long id) {
        if (!songRepo.existsById(id)) {
            throw new ResourceNotFoundException("Requested resource " + id + " not found");
        }
        songRepo.deleteById(id);
    }

    public Song retrieveSongById(Long id) {
        try {
            return songRepo.findById(id).get();

        } catch (Exception e) {
            throw new ResourceNotFoundException("Requested resource " + id + " not found.", e);
        }
    }

    public Album retrieveSongAlbum(Long songId) {
        return retrieveSongById(songId).getAlbum();
    }

    public Song addCommentToSong(Comment commentToAdd, Long songId) {
        Song song = retrieveSongById(songId);
        song.addComment(commentToAdd);
        return songRepo.save(song);
    }
}
