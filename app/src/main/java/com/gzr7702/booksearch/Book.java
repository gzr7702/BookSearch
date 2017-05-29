package com.gzr7702.booksearch;

import java.util.ArrayList;

/**
 * Created by rob on 5/18/17.
 */

public class Book {
    private String title;
    private String authors;
    private String description;

    public Book (String title, String authors, String description) {
        this.title = title;
        this.authors = authors;
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthors() {
        return this.authors;
    }

    public String getDescription() {
        return this.description;
    }
}
