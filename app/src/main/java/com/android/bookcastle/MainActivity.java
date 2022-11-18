package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.android.bookcastle.adapters.CategoryAdapter;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;
    ArrayList<Book> books;
    ArrayList <Book>  PopularBooks;
    ArrayList <Book>  NewBooks;
    ArrayList <Book>  TopBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.parent_rv);
        categories = new ArrayList<Category>();
        books = new ArrayList<Book>();
        PopularBooks = new ArrayList<Book>();
        NewBooks = new ArrayList<Book>();
        TopBooks = new ArrayList<Book>();



        //Popular Books
        PopularBooks.add(new Book("The Alchemist", "Paulo Coelho", "https://m.media-amazon.com/images/P/0008283648.01._SCLZZZZZZZ_SX500_.jpg"));
        PopularBooks.add(new Book("The Power of Now", "Eckhart Tolle", "https://m.media-amazon.com/images/I/41gr3r3FSWL.jpg"));
        PopularBooks.add(new Book("The Alchemist", "Paulo Coelho", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));
        PopularBooks.add(new Book("The Power of Now", "Eckhart Tolle", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));

        //New Books
        NewBooks.add(new Book("The Alchemist", "Paulo Coelho", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));
        NewBooks.add(new Book("The Power of Now", "Eckhart Tolle", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));
        NewBooks.add(new Book("The Alchemist", "Paulo Coelho", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));
        NewBooks.add(new Book("The Power of Now", "Eckhart Tolle", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));

        //Top Books
        TopBooks.add(new Book("The Alchemist", "Paulo Coelho", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));
        TopBooks.add(new Book("The Power of Now", "Eckhart Tolle", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));
        TopBooks.add(new Book("The Alchemist", "Paulo Coelho", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));
        TopBooks.add(new Book("The Power of Now", "Eckhart Tolle", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));

        //Books
        books.add(new Book("The Alchemist", "Paulo Coelho", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));
        books.add(new Book("The Power of Now", "Eckhart Tolle", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));
        books.add(new Book("The Alchemist", "Paulo Coelho", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));
        books.add(new Book("The Power of Now", "Eckhart Tolle", "https://images-na.ssl-images-amazon.com/images/I/51Zt3ZQ3ZzL._SX331_BO1,204,203,200_.jpg"));

        //Categories
        categories.add(new Category("Popular", PopularBooks));
        categories.add(new Category("New", NewBooks));
        categories.add(new Category("Top", TopBooks));
        categories.add(new Category("Books", books));

        //Set Adapter
        categoryAdapter = new CategoryAdapter(categories, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();



    }



}