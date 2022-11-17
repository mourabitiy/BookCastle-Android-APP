package com.android.bookcastle.model;

import java.util.List;

public class Category {
    private String title;
    List<Book> books;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Category(String title, List<Book> books) {
        this.title = title;
        this.books = books;
    }
}
