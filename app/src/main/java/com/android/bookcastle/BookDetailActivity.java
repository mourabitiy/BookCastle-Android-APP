package com.android.bookcastle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bookcastle.models.Book;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    TextView book_title, book_author, book_desc;
    ImageButton back_btn;
    ImageView book_cover;
    ConstraintLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //GET THE BOOK FROM THE INTENT
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");

        super.onCreate(savedInstanceState);
        mainLayout = findViewById(R.id.mainLayout);
        setContentView(R.layout.activity_book_detail);
        book_title = findViewById(R.id.book_title);
        book_author = findViewById(R.id.book_author);
        back_btn = findViewById(R.id.back_btn);
        book_cover = findViewById(R.id.book_cover);

        book_title.setText(book.getTitle());
        book_author.setText(book.getAuthor());
        //set image using glide*
        Glide.with(this).load(book.getImage()).into(book_cover);
        //extract color from book cover image view
        Bitmap bitmap = ((BitmapDrawable) book_cover.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(palette -> {
            int intColor = palette.getDominantColor(0x000000);
            mainLayout.setBackgroundColor(intColor);
        });


        back_btn.setOnClickListener(v -> {
            finish();
        });
    }
    public static int getDominantColor(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }

}