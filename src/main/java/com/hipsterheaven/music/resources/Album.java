package com.hipsterheaven.music.resources;

import javax.persistence.*;
import java.util.*;

@Entity
public class Album {
    private String title;
    private String artistName;
    private String recordLabel;
    private String description;
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "album")
    private List<Song> songs;
    @ElementCollection
    private Map<Long, Comment> comments;

    protected Album() {
    }

    public Album(String title, String artistName, String recordLabel, String description) {
        this.title = title;
        this.artistName = artistName;
        this.recordLabel = recordLabel;
        this.description = description;
        comments = new TreeMap<>();
    }

    public void addComment(Comment newComment) {
        comments.put(newComment.getId(), newComment);
    }

    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getRecordLabel() {
        return recordLabel;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public  Map<Long,Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Album{" +
                "title='" + title + '\'' +
                ", artistName='" + artistName + '\'' +
                ", recordLabel='" + recordLabel + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", comments='" + comments + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(title, album.title) && Objects.equals(artistName, album.artistName) && Objects.equals(recordLabel, album.recordLabel) && Objects.equals(description, album.description) && Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artistName, recordLabel, description, id);
    }
}
