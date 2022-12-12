package com.android.bookcastle.models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {
    private String id;
    private String title;
    private String author;
    private String description;
    private String image;
    private double rating;
    private int pages;
    private String language;
    private int download_count;
    private String content;
    private String download_url;

    public Book(String id, String title, String author, String description, String image, double rating, int pages) {
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

    /*For the sake of testing, I'm using a fake description because the api doesn't provide one
    but in a real app, you would use the description provided by your specific api*/
    public String getDescription() {
        return
            "This is my book summary of The Compound Effect by Darren Hardy. My notes are informal and often contain quotes from the book as well as my own thoughts. This summary also includes key lessons and important passages from the book.\n" +
                    "\n" +
                    "“Talk about things that matter with people who care.” -Jim Rohn\n" +
                    "The compound effect is the operating system that has been running your life whether you know it or not.\n" +
                    "“There are no new fundamentals.” -Jim Rohn\n" +
                    "Success is doing a half dozen things really well, repeated five thousand times.\n" +
                    "You don't need more knowledge. You need a new plan of action.\n" +
                    "Consistency is the ultimate key to success.\n" +
                    "If you aren't better, work harder.\n" +
                    "The compound effect is the strategy of reaping huge rewards from small, seemingly insignificant actions.\n" +
                    "Small choices + consistency + time = significant results.\n" +
                    "Tony Robbins' no man's land concept is when you're not really happy about your life, but you're not unhappy enough to do anything about it. You want to avoid this complacency.\n" +
                    "Knowledge uninvested is wasted.\n" ;
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }


}
