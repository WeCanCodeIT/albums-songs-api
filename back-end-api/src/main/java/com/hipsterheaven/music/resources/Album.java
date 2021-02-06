package com.hipsterheaven.music.resources;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Album {
    private String title;
    private String artistName;
    private String recordLabel;
    private String description;
    private String imageUrl;
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs;
    @ElementCollection
    private List<Comment> comments;

    protected Album() {
    }

    public Album(String title, String artistName, String recordLabel, String description, String imageUrl) {
        this.title = title;
        this.artistName = artistName;
        this.recordLabel = recordLabel;
        this.description = description;
        this.imageUrl = imageUrl;
        comments = new ArrayList<>();
    }

    public void addComment(Comment newComment) {
        comments.add(newComment);
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

    public List<Comment> getComments() {
        return comments;
    }

    public String getImageUrl() {
        return imageUrl;
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
