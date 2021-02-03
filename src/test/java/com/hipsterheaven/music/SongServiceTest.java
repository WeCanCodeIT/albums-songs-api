package com.hipsterheaven.music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SongServiceTest {

    @Test
    public void songServiceShouldSaveSong() {
        Album testAlbum = new Album("Sample Album", "Sample Artist", "Testing Records", "This is a testing sample.");
        Song testSong = new Song("Sample Song", "4:31", 5, testAlbum);
    }

}
