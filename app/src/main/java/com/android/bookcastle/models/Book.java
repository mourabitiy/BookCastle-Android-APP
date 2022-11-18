package com.android.bookcastle.models;

public class Book {
    private String name;
    private String author;
    private String description;
    private String image;

    public Book(String name, String author, String description, String image) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.image = image;
    }

    public Book(String name, String author, String image) {
        this.name = name;
        this.author = author;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
