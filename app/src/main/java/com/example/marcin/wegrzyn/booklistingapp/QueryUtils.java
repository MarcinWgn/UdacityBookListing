package com.example.marcin.wegrzyn.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Marcin on 29.05.2017.
 */

public class QueryUtils {

    public static final String TAG = QueryUtils.class.getName();
    public static final String GET = "GET";


    public QueryUtils() {
    }

    public static ArrayList<Book> extractBookData(String stringUrl){

        URL url = createURL(stringUrl);
        String jResponse = null;

        try {
            jResponse = httpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Book> bookList = extractFromJason(jResponse);

        return bookList;
    }

    private static ArrayList<Book> extractFromJason(String jResponse) {

        if(TextUtils.isEmpty(jResponse)) return null;

        ArrayList<Book> books = new ArrayList<>();


        JSONObject jsonObject = null;

        String title="";
        String subtitle="";

        try {
            jsonObject = new JSONObject(jResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("items");



            for (int i = 0 ; i<jsonArray.length(); i++){



                JSONObject object = jsonArray.getJSONObject(i);
                JSONObject objectVolumeInfo = object.getJSONObject("volumeInfo");

                title = objectVolumeInfo.getString("title");

                if(objectVolumeInfo.has("subtitle")){
                    subtitle = objectVolumeInfo.getString("subtitle");
                }

                JSONArray autorArray = objectVolumeInfo.getJSONArray("authors");
                ArrayList<String> autors = new ArrayList<>();

                for (int j=0 ;j<autorArray.length(); j++){
                autors.add(autorArray.getString(j));
                }
                books.add(new Book(title,subtitle,autors));

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,"Error Parsing"+e);
        }


        return books;
    }

    private static URL createURL(String stringUrl) {

        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error URL ", e);
        }
        return url;
    }

    private static String httpRequest(URL url) throws IOException {

        String response = "";

        if(url == null) return response;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod(GET);
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                response = readStringFromStream(inputStream);

            }else Log.e(TAG, "incorrect response code: " + urlConnection.getResponseCode());


        } catch (IOException e) {
            Log.e(TAG,"Problem JSON data: "+e);
            e.printStackTrace();
        } finally {
            if(urlConnection != null) urlConnection.disconnect();
            if(inputStream != null) inputStream.close();
        }

        return response;
    }

    private static String readStringFromStream(InputStream inputStream) throws IOException {

        StringBuilder result = new StringBuilder();

        if(inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String stringLine = bufferedReader.readLine();

            while (stringLine != null){
                result.append(stringLine);
                stringLine = bufferedReader.readLine();
            }
        }
        return result.toString();
    }


}
