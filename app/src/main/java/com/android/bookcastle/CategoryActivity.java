package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.android.bookcastle.adapters.BooksByCategoryAdapter;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView books_by_category_rv;
    BooksByCategoryAdapter booksByCategoryAdapter;
    private ArrayList<Book> books;
    TextView category_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initViews();
        initMetaData();
        initRecyclerView();


    }

    private void initRecyclerView() {
        booksByCategoryAdapter = new BooksByCategoryAdapter(books, this);
        books_by_category_rv.setAdapter(booksByCategoryAdapter);

    }

    private void initMetaData() {
        //get the books passed from the previous activity
        Category category  = (Category) getIntent().getSerializableExtra("category");
        category_name.setText(category.getTitle());
        books = (ArrayList<Book>) category.getBooks();
    }

    private void initViews() {
        category_name = findViewById(R.id.category_name);
        books_by_category_rv = findViewById(R.id.books_by_category_rv);
        books_by_category_rv.setAdapter(booksByCategoryAdapter);
        books_by_category_rv.setLayoutManager(new LinearLayoutManager(this));

    }
}