package com.hipsterheaven.music.services;

import com.hipsterheaven.music.exceptions.ResourceNotFoundException;
import com.hipsterheaven.music.repositories.SongRepository;
import com.hipsterheaven.music.resources.Song;

public class SongService {
    private SongRepository songRepo;

    public SongService(SongRepository songRepo) {
        this.songRepo = songRepo;
    }

    public void save(Song song) {
        songRepo.save(song);
    }

    public Iterable<Song> retrieveAllSong() {
        return songRepo.findAll();
    }


    public void delete(Long id) {
        songRepo.deleteById(id);
    }

    public Song retrieveSongById(Long id) {
        try {
            return songRepo.findById(id).get();

        } catch (Exception e) {
            throw new ResourceNotFoundException("Requested resource " + id + " not found.", e);
        }
    }
}
