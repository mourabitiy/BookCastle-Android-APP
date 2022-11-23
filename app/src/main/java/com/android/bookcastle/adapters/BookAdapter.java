package com.android.bookcastle.adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bookcastle.BookDetailActivity;
import com.android.bookcastle.R;
import com.android.bookcastle.models.Book;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private ArrayList<Book> books;
    private Context context;
    private OnBookClickListener mOnBookListener;

    public interface OnBookClickListener {
        void onBookClick(int position);
    }

    public void setOnBookClickListener(OnBookClickListener onBookClickListener) {
        mOnBookListener = onBookClickListener;
    }

    public BookAdapter(ArrayList<Book> books, Context context) {
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_rv_layout, null, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bookName.setText(book.getTitle().length()>=20?
                book.getTitle().substring(0,20) + "...":book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());
        Glide.with(context)
                .load(book.getImage())
              .centerCrop()
                .into(holder.bookImage);

        //on click listener for each book
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open the book details activity
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("book", book);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView bookName, bookAuthor;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.book_image);
            bookName = itemView.findViewById(R.id.book_name);
            bookAuthor = itemView.findViewById(R.id.book_author);
        }
    }
}
