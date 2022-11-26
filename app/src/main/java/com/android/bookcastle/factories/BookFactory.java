package com.android.bookcastle.factories;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.bookcastle.api.ApiClient;
import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.Category;
import com.android.bookcastle.utils.ECategories;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class BookFactory {

    ApiClient api = new ApiClient();
    ArrayList<Category> categories;
    ArrayList<Book> books;
    SharedPreferences sharedPreferences;
    Context context;
    public BookFactory() {
        context = null;
    }

    public ArrayList<Book> getBooks(ECategories c, int shelve_capacity) throws IOException {

         books = api.getBooks(c, shelve_capacity);
        return books;
    }


    public ArrayList<Category> getCategories() throws IOException {
        return categories;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Category> categories) {
        //fill the books object with all books from categories
        this.categories = categories;
        categories.stream().forEach(category -> {
            books.addAll(category.getBooks());
        });
    }
}
