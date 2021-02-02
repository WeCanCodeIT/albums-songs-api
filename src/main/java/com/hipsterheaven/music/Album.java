package com.hipsterheaven.music;

public class Album {
    private String title;
    private String artistName;
    private String recordLabel;
    private String description;

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
}
