package com.android.bookcastle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.android.bookcastle.factories.BookFactory;
import com.android.bookcastle.fragments.BookmarkFragment;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.utils.LoadingDialog;
import com.android.bookcastle.utils.UserDatabaseHelper;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class BookDetailActivity extends AppCompatActivity {
    TextView book_title, book_author, book_desc;
    ImageView book_cover;
    Button btn_read, btn_download;
    FloatingActionButton btn_back;
    TextView book_language;
    TextView read_count;
    TextView pages;
    TextView book_description;
    RatingBar book_rating;
    BookFactory mBookFactory;
    FloatingActionButton btn_fav;
    BookmarkFragment bookmarkFragment = new BookmarkFragment();
    UserDatabaseHelper DB;
    boolean isFav;
    Book book;
    LoadingDialog loadingDialog;
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
        btn_download = findViewById(R.id.btn_download);
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

        if(book.getDownload_url() == null){
            //disable download button
            btn_download.setEnabled(false);
Drawable drawable = btn_download.getBackground();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.df_transparent_black_background));
            btn_download.setBackground(drawable);
            //change text
            btn_download.setText("Download Not Available");
        }

        btn_back.setOnClickListener(v -> {
            onBackPressed();
            if(loadingDialog != null){
                loadingDialog.dismissDialog();
            }

        });
        btn_read.setOnClickListener(v -> {
             loadingDialog = new LoadingDialog(BookDetailActivity.this);
            loadingDialog.startLoadingDialog();
            if(book.getContent() == null) {
                mBookFactory = new BookFactory();
                GetBookContent selectedBook = new GetBookContent(book.getId());
                selectedBook.execute();
            }
            else{
                showContent(book);
            }
        });
        btn_download.setOnClickListener(v -> {
           Snackbar.make(v, "Download Started", Snackbar.LENGTH_SHORT).show();
            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(book.getDownload_url());
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle(book.getTitle());
            downloadManager.enqueue(request);
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
            loadingDialog.dismissDialog();
            showContent(book);


        }
    }

    private void showContent(Book loadedBook) {
        Intent intent1 = new Intent(BookDetailActivity.this, ReadBookActivity.class);
        intent1.putExtra("book", loadedBook);
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
            bookmarkFragment.onResume();


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