package com.android.bookcastle.api;

import com.android.bookcastle.models.Book;
import com.android.bookcastle.utils.ECategories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClient {

    OkHttpClient client;



    public ArrayList<Book> getBooks(ECategories c, int shelve_capacity) throws IOException {
        client = new OkHttpClient();
        Request request;
        if(shelve_capacity == -1){
             request = new Request.Builder()
                    .url("http://gutendex.com/books?topic=" + c)
                    .get()
                    .build();
        }
        else{//keep adding ?ids=1,2,3,4,5,6,7,8,9,10 to the url while the shelve_capacity is not reached
            String url = "http://gutendex.com/books?ids=";
            int i = 0;
            while(shelve_capacity > 0){
                url += i;
                i++;
                shelve_capacity--;
                if(shelve_capacity > 0){
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
                book.setTitle(bookObject.getString("title"));
                //author is an array of objects
                JSONArray authors = bookObject.getJSONArray("authors");
                String author = "";
                for (int j = 0; j < authors.length(); j++) {
                    JSONObject authorObject = authors.getJSONObject(j);
                    author += authorObject.getString("name") + ", ";
                }
                book.setAuthor(author);
                JSONObject formats = bookObject.getJSONObject("formats");
                book.setImage(formats.getString("image/jpeg"));
                JSONArray languages = bookObject.getJSONArray("languages");
                String language = "";
                for (int j = 0; j < languages.length(); j++) {
                    if(j == languages.length() - 1) {
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

                String text;
                if (formats.has("text/plain; charset=utf-8")) {
                    text = formats.getString("text/plain; charset=utf-8");
                    book.setPages((text.length()/1000));
                } else if (formats.has("text/plain; charset=us-ascii")) {
                        text = formats.getString("text/plain; charset=us-ascii");
                        book.setPages((text.length()/1000));
                } else if (formats.has("text/plain")) {
                     text = formats.getString("text/plain");
                    book.setPages((text.length()/1000));
                } else {
                    book.setPages(0);
                }

                classics.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();


        }
        return classics;
    }



}
