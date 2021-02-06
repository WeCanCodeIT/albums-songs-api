package com.hipsterheaven.music.resources;

import javax.persistence.Embeddable;
import java.util.Objects;


@Embeddable
public class Comment {

    private String body;
    private String author;

    protected Comment() {

    }

    public Comment(String body, String author) {
        this.body = body;
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "body='" + body + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(body, comment.body) && Objects.equals(author, comment.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, author);
    }

}
