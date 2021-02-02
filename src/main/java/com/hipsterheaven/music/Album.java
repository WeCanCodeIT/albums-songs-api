package com.hipsterheaven.music;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
@Entity
public class Album {
    private String title;
    private String artistName;
    private String recordLabel;
    private String description;
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany
    private Collection<Song> songs;

    protected Album(){}

    public Album(String title, String artistName, String recordLabel, String description) {
        this.title = title;
        this.artistName = artistName;
        this.recordLabel = recordLabel;
        this.description = description;
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

    public Collection<Song> getSongs() {
        return songs;
    }
}
