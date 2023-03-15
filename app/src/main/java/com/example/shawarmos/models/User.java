package com.example.shawarmos.models;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.shawarmos.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    @NonNull
    public String userId;
    public String userName;
    public String email;
    public String avatarUrl;
    public Long lastUpdated;

    static final String USER_ID = "userId";
    static final String USER_NAME = "userName";
    static final String AVATAR_URL = "avatarUrl";
    static final String EMAIL = "email";

    public static final String COLLECTION = "users-info";
    public static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "users_local_last_update";


    public User(@NonNull String userId, String userName, String email, String imgUrl) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.avatarUrl = imgUrl;
    }

    public User(@NonNull String userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.avatarUrl = "";
    }

    public User() {}

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(USER_ID, userId);
        json.put(USER_NAME, userName);
        json.put(EMAIL, email);
        json.put(AVATAR_URL, avatarUrl);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());

        return json;
    }

    public static User fromJson(Map<String, Object> json) {
        String userId = (String) json.get(USER_ID);
        String userName = (String) json.get(USER_NAME);
        String email = (String) json.get(EMAIL);
        String imageUrl = (String) json.get(AVATAR_URL);

        User user = new User(userId, userName, email);
        user.setAvatarUrl(imageUrl);

        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            user.setLastUpdated(time.getSeconds());
        } catch(Exception e) {

        }

        return user;
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

}
