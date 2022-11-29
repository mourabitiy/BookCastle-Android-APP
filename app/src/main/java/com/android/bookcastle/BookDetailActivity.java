package com.android.bookcastle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.palette.graphics.Palette;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.bookcastle.api.ApiClient;
import com.android.bookcastle.factories.BookFactory;
import com.android.bookcastle.fragments.HomeFragment;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;
import com.android.bookcastle.models.User;
import com.android.bookcastle.utils.ECategories;
import com.android.bookcastle.utils.PaletteUtils;
import com.android.bookcastle.utils.UserDatabaseHelper;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    TextView book_title, book_author, book_desc;
    ImageView book_cover;
    Button btn_read;
    FloatingActionButton btn_back;
    TextView book_language;
    TextView read_count;
    TextView pages;
    TextView book_description;
    RatingBar book_rating;
    BookFactory mBookFactory;
    FloatingActionButton btn_fav;
    UserDatabaseHelper DB;
    boolean isFav;
    Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        DB = UserDatabaseHelper.getInstance(this);
        isFav = DB.isFav(book.getId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        btn_read = findViewById(R.id.btn_read);
        btn_fav = findViewById(R.id.btn_fav);
        book_title = findViewById(R.id.book_title);
        book_language = findViewById(R.id.language);
        book_author = findViewById(R.id.book_author);
        book_description = findViewById(R.id.book_description);
        book_rating = findViewById(R.id.book_rating);
        btn_back = findViewById(R.id.btn_back);
        book_cover = findViewById(R.id.book_cover);
        read_count = findViewById(R.id.read_count);
        pages = findViewById(R.id.pages);
        book_title.setText(book.getTitle());
        book_author.setText("Written by : " + book.getAuthor());
        Glide.with(this).load(book.getImage()).into(book_cover);
        book_language.setText(book.getLanguage());
        read_count.setText(String.valueOf(book.getDownload_count()));
        pages.setText(String.valueOf(book.getPages()));
        book_description.setText(book.getDescription());
        book_rating.setRating((float) book.getRating());


        btn_back.setOnClickListener(v -> {
            onBackPressed();

        });
        btn_read.setOnClickListener(v -> {
            mBookFactory = new BookFactory();
            GetBookContent selectedBook = new GetBookContent(book.getId());
            selectedBook.execute();
        });

        if(isFav){
            Drawable drawable = btn_fav.getDrawable();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.breaker));
        }
        btn_fav.setOnClickListener(v -> {
            saveBookToDatabase();

        });
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
    class GetBookContent extends AsyncTask<Void, Void, String> {
        private String id;
        public GetBookContent(String id) {
            this.id = id;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String content = null;
            try {
                content = mBookFactory.GetBookContentById(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }
        @Override
        protected void onPostExecute(String content) {
            super.onPostExecute(content);
            book.setContent(content);

           showContent();


        }
    }

    private void showContent() {
        Intent intent1 = new Intent(BookDetailActivity.this, ReadBookActivity.class);
        intent1.putExtra("book", book);
        startActivity(intent1);
    }

    //Saving book to database
    private void saveBookToDatabase() {
        if(isFav){
            DB.removeBook(book.getId());
            isFav = false;
            Drawable drawable = btn_fav.getDrawable();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.fav_icon_tint));
            Snackbar.make(btn_fav, "Removed from favorites", Snackbar.LENGTH_SHORT).show();
        }
        else{
            DB.addBook(book);
            isFav = true;
            Drawable drawable = btn_fav.getDrawable();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.breaker));
            Snackbar.make(btn_fav, "Added to favorites", Snackbar.LENGTH_SHORT).show();
        }
    }
}