package com.example.shawarmos.models;

import android.media.Image;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

@Entity
public class ReviewModel implements Serializable {

    @NonNull
    @PrimaryKey
    public String reviewId;
    public String title;
    public String description;
    public float rating;
    public String author;
    public String imageUrl;

    static final String TITLE = "title";
    static final String ID = "reviewId";
    static final String IMAGE = "imageUrl";
    static final String RATING = "rating";
    public static final String COLLECTION = "reviews";
    static final String LAST_UPDATED = "lastUpdated";

    public ReviewModel(String title, String description, float rating) {
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public static ReviewModel fromJson(Map<String,Object> json){
        String reviewId = (String)json.get(ID);
        String title = (String)json.get(TITLE);
        String imageUrl = (String)json.get(IMAGE);
        float rating = (float)json.get(RATING);

        ReviewModel review = new ReviewModel(reviewId, title, rating);

        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            //review.setLastUpdated(time.getSeconds());
        } catch(Exception e) {

        }

        return review;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getReviewId());
        json.put(TITLE, getTitle());
        json.put(IMAGE, getImageUrl());
        json.put(RATING, getRating());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());

        return json;
    }

    public ReviewModel(String title, String description) {
        this(title, description, (float)2);
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

    public void setRating(float rating) {
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

    public float getRating() {
        return rating;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
