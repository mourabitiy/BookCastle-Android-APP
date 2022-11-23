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
                "\"AN EPIC BATTLE THAT LASTED TEN YEARS. A LEGENDARY STORY THAT HAS SURVIVED THOUSANDS.'An inimitable retelling of the siege of Troy . . . Fry's narrative, artfully humorous and rich in detail, breathes life and contemporary relevance into these ancient tales' OBSERVER'Stephen Fry has done it again. Well written and super storytelling' 5***** READER REVIEW ________ 'Troy. The most marvellous kingdom in all the world. The Jewel of the Aegean. Glittering Ilion, the city that rose and fell not once but twice . . .'When Helen, the beautiful Greek queen, is kidnapped by the Trojan prince Paris, the most legendary war of all time begins.Watch in awe as a thousand ships are launched against the great city of Troy.Feel the fury of the battleground as the Trojans stand resolutely against Greek might for an entire decade.And witness the epic climax - the wooden horse, delivered to the city of Troy in a masterclass of deception by the Greeks . . .In Stephen Fry's exceptional retelling of our greatest story, TROY will transport you to the depths of ancient Greece and beyond. ________ 'A fun romp through the world's greatest story. Fry's knowledge of the world - ancient and modern - bursts through' Daily Telegraph'An excellent retelling . . . told with compassion and wit' 5***** Reader Review'Hugely successful, graceful' The Times'If you want to read about TROY, this book is a must over any other' 5***** Reader Review'Fluent, crisp, nuanced, begins with a bang' The Times Literary Supplement'The characters . . . are brilliantly brought to life' 5***** Reader Review PRAISE FOR STEPHEN FRY'S GREEK SERIES: 'A romp through the lives of ancient Greek gods. Fry is at his story-telling best . . . the gods will be pleased' Times 'A head-spinning marathon of legends' Guardian 'An Olympian feat. The gods seem to be smiling on Fry - his myths are definitely a hit' Evening Standard 'An odyssey through Greek mythology. Brilliant . . . all hail Stephen Fry' Daily Mail 'A rollicking good read' Independent\"";
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
}
