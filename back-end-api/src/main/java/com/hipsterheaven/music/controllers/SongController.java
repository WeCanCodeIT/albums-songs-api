package com.hipsterheaven.music.controllers;

import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.resources.Comment;
import com.hipsterheaven.music.resources.Song;
import com.hipsterheaven.music.services.SongService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    private SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("")
    public Iterable<Song> retrieveAllSongs() {
        return songService.retrieveAllSongs();
    }

    @GetMapping("/{id}")
    public Song retrieveSong(@PathVariable Long id) {
        return songService.retrieveSongById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable Long id) {
        songService.delete(id);
    }

    @GetMapping("/{id}/album")
    public Album retrieveSongAlbum(@PathVariable Long id) {
        return songService.retrieveSongAlbum(id);
    }

    @PatchMapping("/{id}/comments")
    public Song addCommentToSong(@RequestBody Comment comment, @PathVariable Long id) {
        return songService.addCommentToSong(comment, id);
    }
}
