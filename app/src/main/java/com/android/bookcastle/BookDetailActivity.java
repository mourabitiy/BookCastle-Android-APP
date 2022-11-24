package com.android.bookcastle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bookcastle.models.Book;
import com.android.bookcastle.utils.PaletteUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    TextView book_title, book_author, book_desc;
    ImageButton back_btn;
    ImageView book_cover;
    Button btn_read;
    TextView book_language;
    TextView read_count;
    TextView pages;
    TextView book_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //GET THE BOOK FROM THE INTENT
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        btn_read = findViewById(R.id.btn_read);
        book_title = findViewById(R.id.book_title);
        book_language = findViewById(R.id.language);
        book_author = findViewById(R.id.book_author);
        book_description = findViewById(R.id.book_description);
        //back_btn = findViewById(R.id.back_btn);
        book_cover = findViewById(R.id.book_cover);
        read_count = findViewById(R.id.read_count);
        pages = findViewById(R.id.pages);
        book_title.setText(book.getTitle());
        book_author.setText("Written by : " + book.getAuthor());
        //set image using glide*
        Glide.with(this).load(book.getImage()).into(book_cover);
        book_language.setText(book.getLanguage());
        read_count.setText(String.valueOf(book.getDownload_count()));
        pages.setText(String.valueOf(book.getPages()));
        book_description.setText(book.getDescription());


//        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.book1);
//
//        book_cover.setImageBitmap(imageBitmap);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
//        int width = displayMetrics.widthPixels;
//
//        Bitmap backgroundDominantColorBitmap =PaletteUtils.getDominantGradient(imageBitmap,height,width);
//        book_cover.setImageBitmap(backgroundDominantColorBitmap);
//        back_btn.setOnClickListener(v -> {
//            finish();
//        });
    }


}