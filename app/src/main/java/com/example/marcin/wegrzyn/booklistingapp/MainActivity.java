package com.example.marcin.wegrzyn.booklistingapp;

import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    public static final int QueryLoaderID = 3;
    private EditText editText;
    private android.app.LoaderManager loaderManager;
    public static final String URL = "URL";
    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);

        loaderManager = getLoaderManager();
        loaderManager.initLoader(QueryLoaderID,null,this);

    }


    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return new QueryLoader(this,URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {
        Toast.makeText(this,data.get(0).getTitle(),Toast.LENGTH_SHORT).show();

        editText.setText(data.get(0).getTitle());
        Log.d(TAG,data.get(0).getTitle());

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {

    }
}
