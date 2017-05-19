package com.gzr7702.booksearch;

import java.util.ArrayList;

/**
 * Created by rob on 5/18/17.
 */

public class Book {
    private String title;
    private ArrayList<String> authors;
    private int cover;

    public Book (String title, ArrayList<String> authors, int cover) {
        this.title = title;
        this.authors = authors;
        this.cover = cover;
    }

    public String getTitle() {
        return this.title;
    }

    public ArrayList<String> getAuthors() {
        return this.authors;
    }

    public int getCover() {
        return this.cover;
    }
}
