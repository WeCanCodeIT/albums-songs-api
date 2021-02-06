package com.hipsterheaven.music;

import com.hipsterheaven.music.resources.Album;
import com.hipsterheaven.music.resources.Comment;
import com.hipsterheaven.music.resources.Song;
import com.hipsterheaven.music.services.AlbumService;
import com.hipsterheaven.music.services.SongService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Populator implements CommandLineRunner {

    AlbumService albumService;
    SongService songService;

    public Populator(AlbumService albumService, SongService songService) {
        this.albumService = albumService;
        this.songService = songService;
    }

    @Override
    public void run(String... args) throws Exception {
        Album heavenTonight = new Album("Heaven Tonight", "Cheap Trick", "Epic Records",
                "Heaven Tonight is considered Cheap Trick's best album by many fans and critics.", "http://placekitten.com/200/300");
        albumService.save(heavenTonight);
        Song surrender = new Song("Surrender", "4:16", 10, heavenTonight, "https://youtu.be/dQw4w9WgXcQ");
        songService.save(surrender);

        Album journeyGreatistHits = new Album("Greatist Hits", "Journey", "Columbia Records",
                "Greatest Hits is a compilation album by the American rock band Journey, originally released in 1988 by Columbia Records. It is the band's best-selling career disc, spending 554 weeks on the Billboard 200 album chart.", "http://placekitten.com/200/300");
        albumService.save(journeyGreatistHits);
        Song dontStopBelievin = new Song("Don't Stop Believin'", "4:10", 10, journeyGreatistHits, "https://youtu.be/dQw4w9WgXcQ");
        songService.save(dontStopBelievin);

        Album so = new Album("So", "Peter Gabriel", "Parlophone Records",
                "Often considered his best and most accessible album, So was an immediate commercial success and transformed Gabriel from a cult artist into a mainstream star, becoming his best-selling solo release.", "http://placekitten.com/200/300");
        so.addComment(new Comment("This album changed my life!  I left college, bought a synth and toured with my Peter Gabriel tribute band in eastern Oregon riding a Volkswagen Rabbit, diesel of course.", "Leon"));
        albumService.save(so);
        Song sledgehammer = new Song("Sledgehammer", "5:12", 9, so, "https://youtu.be/dQw4w9WgXcQ");
        songService.save(sledgehammer);

        Album bluesRoots = new Album("Blues & Roots", "Charles Mingus", "Atlantic Records",
                "\"This record is unusual— it presents only one part of my musical world, the blues.\" -Charles Mingus", "http://placekitten.com/200/300");
        albumService.save(bluesRoots);
        Song moanin = new Song("Moanin'", "7:58", 10, bluesRoots, "https://youtu.be/dQw4w9WgXcQ");
        moanin.addComment(new Comment("Best bari sax solo in a generation!", "Leo P."));
        songService.save(moanin);
        Song wednesdayNightPrayerMeeting = new Song("Wednesday Night Prayer Meeting", "5:45", 8, bluesRoots, "https://youtu.be/dQw4w9WgXcQ");
        songService.save(wednesdayNightPrayerMeeting);

        Album loveBelow = new Album("Speakerboxxx/The Love Below", "OutKast", "Artista Records",
                "Speakerboxxx/The Love Below received widespread acclaim from music critics, who praised the consistency of Big Boi's Speakerboxxx and the eclectic musical style of André 3000's The Love Below.", "http://placekitten.com/200/300");
        albumService.save(loveBelow);
        Song heyYa = new Song("Hey Ya!", "3:55", 11, loveBelow, "https://youtu.be/dQw4w9WgXcQ");
        songService.save(heyYa);
        Song roses = new Song("Roses", "6:09", 7, loveBelow, "https://youtu.be/dQw4w9WgXcQ");
        songService.save(roses);
        Song rooster = new Song("The Rooster", "3:57", 10, loveBelow, "https://youtu.be/dQw4w9WgXcQ");
        rooster.addComment(new Comment("Hugely underrated song!", "Dean"));
        songService.save(rooster);
    }
}
