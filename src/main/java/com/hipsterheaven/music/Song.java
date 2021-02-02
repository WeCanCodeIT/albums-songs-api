package com.hipsterheaven.music;

public class Song {
    private String title;
    private String duration;
    private int rating;
    private Album album;

    public Song(String title, String duration, int rating, Album album) {
        this.title = title;
        this.duration = duration;
        this.rating = rating;
        this.album = album;
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

    public Album getAlbum() {
        return album;
    }
}
