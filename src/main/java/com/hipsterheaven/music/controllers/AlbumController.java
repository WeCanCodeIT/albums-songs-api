package com.hipsterheaven.music.controllers;

import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.services.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
