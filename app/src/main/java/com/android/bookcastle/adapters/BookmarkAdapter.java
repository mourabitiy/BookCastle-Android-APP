package com.android.bookcastle.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bookcastle.BookDetailActivity;
import com.android.bookcastle.R;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.utils.UserDatabaseHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    UserDatabaseHelper DB;
    private ArrayList<Book> books;
    private Context context;

    public BookmarkAdapter(ArrayList<Book> books, Context context) {
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book_bookmark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);
        DB = UserDatabaseHelper.getInstance(context);
        //if title is too long, then it will be cut off
        String title = book.getTitle();
        if (title.length() > 20) {
            title = title.substring(0, 20) + "...";
        }
        holder.book_title.setText(title);
        holder.book_author.setText("By " + book.getAuthor());
        holder.book_rating.setRating((float) book.getRating());
        Glide.with(context).load(book.getImage()).into(holder.book_image);
        if(book.getPages() == 0) {
            holder.book_pages.setText("Unknown | " + book.getDownload_count() + " Downloads");
        } else {
            holder.book_pages.setText(book.getPages() + " pages" + " | " + book.getDownload_count() + " Downloads");
        }

        holder.book_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fade fade = new Fade();
                View decor = ((Activity) context).getWindow().getDecorView();
                fade.excludeTarget(android.R.id.statusBarBackground, true);
                fade.excludeTarget(android.R.id.navigationBarBackground, true);
                ((Activity) context).getWindow().setEnterTransition(fade);
                ((Activity) context).getWindow().setExitTransition(fade);

                Intent intent = new Intent(context, BookDetailActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.book_image, "coverTN");
                intent.putExtra("book", book);
                context.startActivity(intent, options.toBundle());
            }
        });
        holder.bookmark.setOnClickListener(v -> {
            DB.deleteBookmark(book.getId());
            holder.itemView.animate().alpha(0).setDuration(300).withEndAction(() -> {
                //create a swipe animation
                holder.itemView.setTranslationX(-holder.itemView.getWidth());
                holder.itemView.animate().translationX(0).setDuration(300).withEndAction(() -> {
                    books.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, books.size());
                });

            });
            if(books.size() == 0) {
                ((Activity) context).findViewById(R.id.bookmark_empty).setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView book_title, book_price,  book_author, book_pages;
        RatingBar book_rating;
        ImageView book_image, bookmark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_title = itemView.findViewById(R.id.book_title_all);
            book_rating = itemView.findViewById(R.id.book_rating);
            book_image = itemView.findViewById(R.id.book_cover_all);
            book_author = itemView.findViewById(R.id.book_author_all);
            book_pages = itemView.findViewById(R.id.book_pages_reads);
            bookmark = itemView.findViewById(R.id.bookmark);

        }

    }
}