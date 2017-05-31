package com.example.marcin.wegrzyn.booklistingapp;

import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    public static final int QueryLoaderID = 3;
    public static final String URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public static final String EndURL = "&maxResults=10";
    public static final String TAG = MainActivity.class.getName();


    private EditText editText;
    private Button button;
    private String searchString;
    private BookAdapter bookAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"OnCreate");


        ListView listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);


        bookAdapter = new BookAdapter(this,new ArrayList<Book>());
        listView.setAdapter(bookAdapter);

        getLoaderManager().initLoader(QueryLoaderID,null,MainActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchString = String.valueOf(editText.getText());

                if(searchString.length()>0){
                    getLoaderManager().restartLoader(QueryLoaderID,null,MainActivity.this).forceLoad();
                }else {
                    Toast.makeText(MainActivity.this,"Enter the word",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean isNetworkConnected(){

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this,URL+searchString+EndURL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {

        Log.d(TAG,String.valueOf(data.size()));

        bookAdapter.clear();

        if(data != null && !data.isEmpty() ){

            bookAdapter.addAll(data);
            Log.d(TAG,data.get(0).getTitle());

        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {

        bookAdapter.clear();
    }
}
