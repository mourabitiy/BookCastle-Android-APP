package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.bookcastle.models.Book;
import com.android.bookcastle.utils.UserDatabaseHelper;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;

import java.util.Timer;
import java.util.TimerTask;

public class ReadBookActivity extends AppCompatActivity {

    FloatingActionButton btn_back;
    TextView book_title, book_author, book_content;
    Slider slider;
    long startTime;
    long endTime;
    UserDatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        DB  = UserDatabaseHelper.getInstance(this);
        btn_back = findViewById(R.id.btn_back);
        book_title = findViewById(R.id.book_title);
        book_author = findViewById(R.id.book_author);
        book_content = findViewById(R.id.book_content);
        slider = findViewById(R.id.slider);

        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");
        if (book.getTitle().length() > 20) {
            book_title.setText(book.getTitle().substring(0, 20) + "...");
        } else {
            book_title.setText(book.getTitle());
        }
        book_author.setText(book.getAuthor());
        book_content.setText(book.getContent());

        btn_back.setOnClickListener(v -> {
            onBackPressed();

        });
        //change slider range
        slider.setValueFrom(18);
        slider.setValue(18);
        slider.setValueTo(40);

        //bind the slider to the textview
        slider.addOnChangeListener((slider, value, fromUser) -> {
            book_content.setTextSize(value);
        });
        startTime = System.currentTimeMillis();

    }

    @Override
    protected void onDestroy()
    {
        endTime = System.currentTimeMillis();
        long timeSpend = endTime - startTime;
        //convert to minutes
        timeSpend = timeSpend / 1000;
        DB.updateReadingGoal(timeSpend);
        super.onDestroy();
    }
}