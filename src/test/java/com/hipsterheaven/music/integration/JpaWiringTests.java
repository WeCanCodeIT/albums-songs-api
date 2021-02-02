package com.hipsterheaven.music.integration;

import com.hipsterheaven.music.Album;
import com.hipsterheaven.music.Song;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class JpaWiringTests {
    @Test
    public void albumsShouldHaveAnOneToManyRelationshipWithSongs(){
        Album testAlbum = new Album("Sample Title", "Artist Name", "the label", "test album");

        Song testSong1 = new Song("title", "duration", 5, testAlbum);
        Song testSong2 = new Song("another title", "4:31", 4, testAlbum);


    }
}
