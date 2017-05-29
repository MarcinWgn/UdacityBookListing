package com.example.marcin.wegrzyn.booklistingapp;

import java.util.ArrayList;

/**
 * Created by Marcin on 28.05.2017.
 */

public class Book {

    private String title;
    private String subtitle;
    private ArrayList<String> authors;

    public Book(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public Book(String title, String subtitle, ArrayList<String> authors) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getAuthors() {

        String returnAutors = "";

        for (int i = 0; i < authors.size() ; i++) {
            if (i == 0){
                returnAutors = authors.get(i);
            }else {
                returnAutors += ", "+authors.get(i);
            }
        }
        return returnAutors;
    }
}
