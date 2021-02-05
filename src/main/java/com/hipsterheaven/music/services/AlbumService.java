package com.hipsterheaven.music.services;

import com.hipsterheaven.music.repositories.AlbumRepository;
import com.hipsterheaven.music.exceptions.ResourceNotFoundException;
import com.hipsterheaven.music.resources.Album;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
    private AlbumRepository albumRepo;

    public AlbumService(AlbumRepository albumRepo) {
        this.albumRepo = albumRepo;
    }


    public Album save(Album album) {
        return albumRepo.save(album);
    }

    public Iterable<Album> retrieveAllAlbums() {
        return albumRepo.findAll();
    }


    public void delete(Long id) {
        albumRepo.deleteById(id);
    }

    public Album retrieveAlbumById(Long id) {
        try {
            return albumRepo.findById(id).get();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Requested resource " + id + " not found.", e);
        }
    }
}
