package com.example.marcin.wegrzyn.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Marcin on 28.05.2017.
 */

public class QueryLoader extends AsyncTaskLoader<ArrayList<Book>> {

    public static final String TAG = QueryLoader.class.getName();

    private String stringUrl;

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public QueryLoader(Context context, String stringUrl ) {
        super(context);
        this.stringUrl = stringUrl;
    }

    @Override
    public ArrayList<Book> loadInBackground() {

        if(stringUrl == null) return null;
        // TODO: 28.05.2017 zadania w tle
        Log.d(TAG, "loadInBackground");

        ArrayList<Book> books  = new ArrayList<>();
        books.add(new Book("Pan Tadeusz","Na litwie"));

        return books;
    }
}
