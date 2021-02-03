package com.hipsterheaven.music;

import java.util.List;

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

    public Album retrieveAlbumById(Long id){
        return albumRepo.findById(id).get();
    }
}
