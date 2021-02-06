package com.hipsterheaven.music.controllers;

import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.resources.Comment;
import com.hipsterheaven.music.resources.Song;
import com.hipsterheaven.music.services.AlbumService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {
    private final AlbumService albumService;

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
    public Album createNewAlbum(@RequestBody Album newAlbum) {
        return albumService.save(newAlbum);
    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable Long id) {
        albumService.delete(id);
    }

    @GetMapping("/{id}/songs")
    public Iterable<Song> retrieveAlbumSongs(@PathVariable Long id) {
        return albumService.retrieveAlbumSongs(id);
    }

    @PatchMapping("/{id}/songs")
    public Album addSongToAlbum(@RequestBody Song newSong, @PathVariable Long id) {
        return albumService.addSongToAlbum(newSong, id);
    }

    @PatchMapping("{id}/comments")
    public Album addCommentToAlbum(@RequestBody Comment newComment, @PathVariable Long id) {
        return albumService.addCommentToAlbum(newComment, id);
    }

}
