package com.example.shawarmos.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.shawarmos.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

@Entity
public class Review implements Serializable {

    @PrimaryKey
    @NonNull
    public String reviewId;
    public String title;
    public String description;
    public double rating;
    public String author;
    public String imageUrl;
    public Long lastUpdated;

    static final String TITLE = "title";
    static final String ID = "reviewId";
    static final String DESCRIPTION = "description";
    static final String IMAGE = "imageUrl";
    static final String RATING = "rating";
    static final String AUTHOR = "author";
    public static final String COLLECTION = "reviews";
    public static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "reviews_local_last_update";

    public Review() {}

    public Review(String reviewId, String title, String description, double rating, String author, String imageUrl) {
        this.reviewId = reviewId;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public static Review fromJson(Map<String,Object> json){
        String reviewId = (String)json.get(ID);
        String description = (String)json.get(DESCRIPTION);
        String title = (String)json.get(TITLE);
        String author = (String)json.get(AUTHOR);
        String imageUrl = (String)json.get(IMAGE);
        double rating = (double)json.get(RATING);

        Review review = new Review(reviewId, title, description, rating, author, imageUrl);

        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            review.setLastUpdated(time.getSeconds());
        } catch(Exception e) {

        }

        return review;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getReviewId());
        json.put(DESCRIPTION, getDescription());
        json.put(TITLE, getTitle());
        json.put(IMAGE, getImageUrl());
        json.put(RATING, getRating());
        json.put(AUTHOR, getAuthor());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());

        return json;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }

    @NonNull
    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(@NonNull String reviewId) {
        this.reviewId = reviewId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
