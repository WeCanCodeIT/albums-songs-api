package com.hipsterheaven.music.integration;

import com.hipsterheaven.music.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JpaWiringTests {
    @Autowired
    private AlbumRepository albumRepo;
    @Autowired
    private SongRepository songRepo;
    @Autowired
    private TestEntityManager entityManager;
    private Album testAlbum;
    private Song testSong1;
    private Song testSong2;
    private Comment testComment;

    @BeforeEach
    void setUp() {
        testAlbum = new Album("Sample Title", "Artist Name", "the label", "test album");
        testSong1 = new Song("title", "duration", 5, testAlbum);
        testSong2 = new Song("another title", "4:31", 4, testAlbum);
        albumRepo.save(testAlbum);
        songRepo.save(testSong1);
        songRepo.save(testSong2);
        testComment = new Comment("body", "author");
    }

    @Test
    public void albumsShouldHaveAnOneToManyRelationshipWithSongs(){
        flushAndClearEntityManager();

        Album retrievedAlbum = albumRepo.findById(testAlbum.getId()).get();
        assertThat(retrievedAlbum.getSongs()).contains(testSong1, testSong2);
    }

    @Test
    public void songsShouldHaveManyComments() {
        testSong1.addComment(testComment);
        songRepo.save(testSong1);

        flushAndClearEntityManager();

        Song retrievedSong = songRepo.findById(testSong1.getId()).get();
        assertThat(retrievedSong.getComments()).contains(testComment);
    }
    @Test
    public void albumsShouldHaveManyComments(){
        testAlbum.addComment(testComment);
        albumRepo.save(testAlbum);

        flushAndClearEntityManager();

        Album retrievedAlbum = albumRepo.findById(testAlbum.getId()).get();
        assertThat(retrievedAlbum.getComments()).contains(testComment);
    }

    private void flushAndClearEntityManager() {
        entityManager.flush();
        entityManager.clear();
    }
}
