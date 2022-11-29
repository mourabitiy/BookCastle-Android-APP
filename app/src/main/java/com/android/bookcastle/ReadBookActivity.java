package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.bookcastle.models.Book;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReadBookActivity extends AppCompatActivity {

    FloatingActionButton btn_back;
    TextView book_title, book_author, book_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        btn_back = findViewById(R.id.btn_back);
        book_title = findViewById(R.id.book_title);
        book_author = findViewById(R.id.book_author);
        book_content = findViewById(R.id.book_content);

        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");
        if(book.getTitle().length() > 20){
            book_title.setText(book.getTitle().substring(0, 20) + "...");
        }else{
            book_title.setText(book.getTitle());
        }
        book_author.setText(book.getAuthor());
        book_content.setText(book.getContent());

    btn_back.setOnClickListener(v -> {
             onBackPressed();
        });
    }


}