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



    public ArrayList<Book> getBooks(ECategories c) throws IOException {
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://gutendex.com/books?topic=" + c)
                .get()
                .build();
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
                //formats is an object
                JSONObject formats = bookObject.getJSONObject("formats");
                book.setImage(formats.getString("image/jpeg"));
                classics.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();


        }
        return classics;
    }



}
