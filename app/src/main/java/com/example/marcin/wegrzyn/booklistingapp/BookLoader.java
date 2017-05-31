package com.example.marcin.wegrzyn.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Marcin on 28.05.2017 :)
 */

class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private String stringUrl;

    BookLoader(Context context, String stringUrl) {
        super(context);
        this.stringUrl = stringUrl;
    }

    @Override
    public ArrayList<Book> loadInBackground() {

        if (stringUrl == null) return null;

        return QueryUtils.extractBookData(stringUrl);
    }
}
