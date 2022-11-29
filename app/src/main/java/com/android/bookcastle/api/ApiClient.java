package com.android.bookcastle.api;

import android.util.Log;

import com.android.bookcastle.models.Book;
import com.android.bookcastle.utils.ECategories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.Url;

public class ApiClient {

    OkHttpClient client;


    public ArrayList<Book> getBooks(ECategories c, int shelve_capacity) throws IOException {
        client = new OkHttpClient();
        Request request;
        if (shelve_capacity == -1) {
            request = new Request.Builder()
                    .url("http://gutendex.com/books?topic=" + c)
                    .get()
                    .build();
        } else {//keep adding ?ids=1,2,3,4,5,6,7,8,9,10 to the url while the shelve_capacity is not reached
            String url = "http://gutendex.com/books?ids=";
            int i = 0;
            while (shelve_capacity > 0) {
                url += i;
                i++;
                shelve_capacity--;
                if (shelve_capacity > 0) {
                    url += ",";
                }
            }
            request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
        }

        Response response = client.newCall(request).execute();
        //parse the response
        ArrayList<Book> classics = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bookObject = jsonArray.getJSONObject(i);
                Book book = new Book();
                book.setId(bookObject.getString("id"));
                book.setTitle(bookObject.getString("title"));
                //author is an array of objects
                JSONArray authors = bookObject.getJSONArray("authors");
                String author = "";
                for (int j = 0; j < authors.length(); j++) {
                    JSONObject authorObject = authors.getJSONObject(j);
                    if(j == 0){
                        author += authorObject.getString("name");
                    }else{
                        author += ", " + authorObject.getString("name");
                    }
                }
                book.setAuthor(author);
                JSONObject formats = bookObject.getJSONObject("formats");
                book.setImage(formats.getString("image/jpeg"));
                JSONArray languages = bookObject.getJSONArray("languages");
                String language = "";
                for (int j = 0; j < languages.length(); j++) {
                    if (j == languages.length() - 1) {
                        language += languages.getString(j);
                    } else {
                        language += languages.getString(j) + ", ";
                    }
                }
                book.setLanguage(language.toUpperCase());
                book.setDescription("Dummy Description");
                //get download_count int value
                book.setDownload_count(bookObject.getInt("download_count"));
                //Set a random rating from 1 to 5
                book.setRating(Math.random() * 5);

                book.setPages(414);

                classics.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();


        }
        return classics;
    }

    public String getBookContentById(String id) throws IOException {
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://gutendex.com/books?ids=" + id)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String content = "";
        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bookObject = jsonArray.getJSONObject(i);
                JSONObject formats = bookObject.getJSONObject("formats");
                String url = formats.getString("text/plain; charset=utf-8");
                content = getContentFromUrl(url);
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return content;
    }

    private String getContentFromUrl(String url) {
        String content = "";
        //use okhttp to get the content
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            content = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}