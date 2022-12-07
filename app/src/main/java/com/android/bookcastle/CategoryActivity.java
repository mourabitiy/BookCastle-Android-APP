package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bookcastle.adapters.BooksByCategoryAdapter;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView books_by_category_rv;
    BooksByCategoryAdapter booksByCategoryAdapter;
    private ArrayList<Book> books;
    TextView category_name;
    SearchView mSearchView;
    FloatingActionButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initViews();
        initMetaData();
        initRecyclerView();
        mSearchView = findViewById(R.id.searchView);
        back_btn = findViewById(R.id.back_btn);
        mSearchView.clearFocus();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        back_btn.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void filterList(String newText) {
        ArrayList<Book> filteredList = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(book);
            }
        }
        if (filteredList.size() == 0) {
            Toast.makeText(this, "No Book found", Toast.LENGTH_SHORT).show();
        }
        else{
            booksByCategoryAdapter.filterList(filteredList);
        }

    }

    private void initRecyclerView() {
        booksByCategoryAdapter = new BooksByCategoryAdapter(books, this);
        books_by_category_rv.setAdapter(booksByCategoryAdapter);

    }

    private void initMetaData() {
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