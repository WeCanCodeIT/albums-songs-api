package com.hipsterheaven.music.resources;

public class TestResources {
    public static final Album TEST_ALBUM = new Album("Testing in Test Town", "Testy McTesterson", "Exam Records",
            "Testing the night away.", "http://placekitten.com/200/300");
    public static final Song TEST_SONG = new Song("Sample Song", "4:31", 5, TEST_ALBUM, "https://youtu.be/dQw4w9WgXcQ");
    public static final Comment TEST_COMMENT = new Comment("Testing is fun.", "Testy Tester");
}
