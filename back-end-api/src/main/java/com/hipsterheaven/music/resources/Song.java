package com.hipsterheaven.music.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class Song {
    private String title;
    private String duration;
    private int rating;
    @ManyToOne
    @JsonIgnore
    private Album album;
    private String videoLink;
    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection
    private List<Comment> comments;

    protected Song() {
    }

    public Song(String title, String duration, int rating, Album album, String videoLink) {
        this.title = title;
        this.duration = duration;
        this.rating = rating;
        this.album = album;
        this.videoLink = videoLink;
    }

    public void addAlbum(Album album) {
        this.album = album;
    }

    public void addComment(Comment newComment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(newComment);
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public int getRating() {
        return rating;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public Album getAlbum() {
        return album;
    }

    public Long getId() {
        return id;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                ", rating=" + rating +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return rating == song.rating && Objects.equals(title, song.title) && Objects.equals(duration, song.duration) && Objects.equals(id, song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, duration, rating, album, id);
    }
}
