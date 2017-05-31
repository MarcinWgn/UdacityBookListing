package com.example.marcin.wegrzyn.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Marcin on 28.05.2017.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    public static final String TAG = BookLoader.class.getName();

    private String stringUrl;

    @Override
    protected void onStartLoading() {

//        forceLoad();
    }

    public BookLoader(Context context, String stringUrl ) {
        super(context);
        this.stringUrl = stringUrl;
    }

    @Override
    public ArrayList<Book> loadInBackground() {

        if(stringUrl == null) return null;

        ArrayList<Book> books = QueryUtils.extractBookData(stringUrl);

        Log.d(TAG, "loadInBackground");

        return books;
    }
}
