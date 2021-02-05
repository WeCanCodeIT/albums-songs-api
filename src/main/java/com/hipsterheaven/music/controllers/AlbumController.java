package com.hipsterheaven.music.controllers;

import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.services.AlbumService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {
    private AlbumService albumService;

    public AlbumController(AlbumService albumService) {

        this.albumService = albumService;
    }

    @GetMapping("")
    public Iterable<Album> retrieveAllAlbums() {
        return albumService.retrieveAllAlbums();
    }

    @GetMapping("/{id}")
    public Album retrieveAlbumById(@PathVariable Long id) {
        return albumService.retrieveAlbumById(id);
    }

    @PostMapping("")
    public Album createNewAlbum(@RequestBody Album newAlbum){
        return albumService.save(newAlbum);
    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable Long id){
        albumService.delete(id);
    }
}
