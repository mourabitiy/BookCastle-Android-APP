package com.android.bookcastle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bookcastle.R;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categories = new ArrayList<Category>();
    private Context context;


    private ArrayList<Category> list = new ArrayList<Category>();


    public CategoryAdapter(ArrayList<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parent_rv_layout, null, false);
        return new CategoryViewHolder(view);
    }
    public CategoryAdapter(Context context) {
        // Do not pass a list in the constructor, because the list may be empty
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getTitle());
        //set up child recycler view
        BookAdapter bookAdapter = new BookAdapter((ArrayList<Book>) categories.get(position).getBooks(), context);
        holder.childRecyclerView.setAdapter(bookAdapter);
        holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        holder.childRecyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        RecyclerView childRecyclerView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categorie_tv);
            childRecyclerView = itemView.findViewById(R.id.child_rv);
        }
    }
    public void setList(ArrayList<Category> list) {
        this.categories = list;
        notifyDataSetChanged();
    }
}