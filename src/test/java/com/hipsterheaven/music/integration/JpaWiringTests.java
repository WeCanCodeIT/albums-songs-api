package com.hipsterheaven.music.integration;

import com.hipsterheaven.music.Album;
import com.hipsterheaven.music.AlbumRepository;
import com.hipsterheaven.music.Song;
import com.hipsterheaven.music.SongRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JpaWiringTests {
    @Autowired
    private AlbumRepository albumRepo;
    @Autowired
    private SongRepository songRepo;

    @Test
    public void albumsShouldHaveAnOneToManyRelationshipWithSongs(){
        Album testAlbum = new Album("Sample Title", "Artist Name", "the label", "test album");

        Song testSong1 = new Song("title", "duration", 5, testAlbum);
        Song testSong2 = new Song("another title", "4:31", 4, testAlbum);

        albumRepo.save(testAlbum);
        songRepo.save(testSong1);
        songRepo.save(testSong2);

        Album retrievedAlbum = albumRepo.findById(testAlbum.getId()).get();
        assertThat(retrievedAlbum.getSongs()).contains(testSong1, testSong2);
    }
}
