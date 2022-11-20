package com.android.bookcastle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bookcastle.adapters.CategoryAdapter;
import com.android.bookcastle.api.RetrofitClient;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;
    ArrayList <Book>  PopularBooks;

    RequestQueue requestQueue;

    TextView welcome_msg, test_response;
    String username;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        this.username = sharedPreferences.getString("username", "");
        recyclerView = findViewById(R.id.parent_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categories = new ArrayList<Category>();
        PopularBooks = new ArrayList<Book>();

        requestQueue = Volley.newRequestQueue(this);
        parseJson();




        //adapter


    }

    private void parseJson() {
        String url = "https://www.googleapis.com/books/v1/volumes?q=subject:fiction";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("items");
                //display size of json array using toast
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject book = jsonArray.getJSONObject(i);
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                    String title = volumeInfo.getString("title");
                    String image = volumeInfo.getJSONObject("imageLinks").getString("smallThumbnail");
                    //author is an array
                    JSONArray authors = volumeInfo.getJSONArray("authors");
                    String author = authors.getString(0);
                    Book book1 = new Book(title, author, image);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                categories.add(new Category("Popular", PopularBooks));
                categoryAdapter = new CategoryAdapter(categories, MainActivity.this);
                recyclerView.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
            }
        }, error -> {
            Snackbar.make(recyclerView, "Error fetching data", Snackbar.LENGTH_SHORT).show();
                });

        requestQueue.add(request);
    }

    @Override
    protected void onStart() {
        super.onStart();
        welcome_msg = findViewById(R.id.welcome_msg);
        welcome_msg.setText("Welcome back, " + username);
    }


    
}