package com.android.bookcastle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bookcastle.MainActivity;
import com.android.bookcastle.R;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        holder.categoryName.setText(category.getTitle().substring(0, 1).toUpperCase() + category.getTitle().substring(1));
        //set up child recycler view
        BookAdapter bookAdapter = new BookAdapter((ArrayList<Book>) categories.get(position).getBooks(), context);
        holder.childRecyclerView.setAdapter(bookAdapter);
        holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        holder.childRecyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
        //handlle view_all textview click
        holder.viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //display a new activity and pass the category
                ((MainActivity) context).displayCategoryActivity(category);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

  //create the getFilter method
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    results.count = list.size();
                    results.values = list;
                } else {
                    String searchStr = constraint.toString().toLowerCase();
                    ArrayList<Category> resultData = new ArrayList<Category>();
                    for (Category category : list) {
                        if (category.getTitle().toLowerCase().contains(searchStr)) {
                            resultData.add(category);
                        }
                    }
                    results.count = resultData.size();
                    results.values = resultData;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                categories = (ArrayList<Category>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        RecyclerView childRecyclerView;
        TextView viewAll;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categorie_tv);
            childRecyclerView = itemView.findViewById(R.id.child_rv);
            viewAll = itemView.findViewById(R.id.view_all);
        }
    }
    public void setList(ArrayList<Category> list) {
        this.categories = list;
        notifyDataSetChanged();
    }
}