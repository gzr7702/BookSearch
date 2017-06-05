package com.gzr7702.booksearch;

import java.io.Serializable;

/**
 * Created by rob on 5/18/17.
 */

public class Book {
    private String title;
    private String authors;
    private String publishDate;

    public Book (String title, String authors, String publishDate) {
        super();
        this.title = title;
        this.authors = authors;
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthors() {
        return this.authors;
    }

    public String getPublishDate() {
        return this.publishDate;
    }
}
