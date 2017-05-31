package com.example.marcin.wegrzyn.booklistingapp;

import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    public static final int QueryLoaderID = 3;
    public static final String URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public static final String EndURL = "&maxResults=10";

    private EditText editText;
    private String searchString;
    private BookAdapter bookAdapter;
    private ProgressBar progressBar;
    private ListView listView;
    private TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        emptyView = (TextView) findViewById(R.id.emptyView);

        emptyView.setText(R.string.no_data);
        progressBar.setVisibility(View.GONE);
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        listView.setAdapter(bookAdapter);

        if (isNetworkConnected()) {
            getLoaderManager().initLoader(QueryLoaderID, null, MainActivity.this);
        } else {
            noConnection();
        }


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchString = String.valueOf(editText.getText());

                if (isNetworkConnected()) {

                    isConnection();

                    if (searchString.length() > 0) {
                        getLoaderManager().restartLoader(QueryLoaderID, null, MainActivity.this).forceLoad();
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(MainActivity.this, R.string.enter_word, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    noConnection();
                }
            }
        });
    }

    private void noConnection() {
        listView.setVisibility(View.GONE);
        emptyView.setText(R.string.no_internet);
    }

    private void isConnection() {
        listView.setVisibility(View.VISIBLE);
        emptyView.setText(R.string.null_string);
    }

    private boolean isNetworkConnected() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, URL + searchString + EndURL);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {

        emptyView.setText(R.string.null_string);
        bookAdapter.clear();

        if (data != null && !data.isEmpty()) {
            bookAdapter.addAll(data);
        } else emptyView.setText(R.string.no_data);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        bookAdapter.clear();
    }
}
