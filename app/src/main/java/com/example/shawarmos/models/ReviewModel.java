package com.example.shawarmos.models;

public class ReviewModel {

    public String title;
    public String description;
    public int ratting;
    public String user;
    public String videoUrl;



    public ReviewModel(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
