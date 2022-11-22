package com.android.bookcastle.models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Book {
    private String id;
    private String title;
    private String author;
    private String description;
    private String image;
    private double rating;
    private Integer pages;

    public Book(String id, String title, String author, String description, String image, double rating, Integer pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.pages = pages;
    }

    public Book() {

    }

    public Book(String title, String author, String image) {
        this.title = title;
        this.author = author;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        //replace http with https
       return image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
