package com.example.shawarmos.models;

import android.graphics.Bitmap;

import com.example.shawarmos.models.listeners.IEmptyOnCompleteListener;
import com.example.shawarmos.entities.User;

public class UserModel extends BaseModel {

    private static final String STORAGE_FILE_PATH = "user_images/";

    private static final UserModel _instance = new UserModel();

    public static UserModel instance() {
        return _instance;
    }

    public void createUser(User user, Bitmap bitmap, IEmptyOnCompleteListener listener) {
        if (bitmap != null) {
            storage.uploadImage(bitmap, user.getUserId(), STORAGE_FILE_PATH, url -> {
                user.setAvatarUrl(url);
                dbCollections.createUser(user, () -> {
                    listener.onComplete();
                });
            });
        } else {
            dbCollections.createUser(user, () -> {
                listener.onComplete();
            });
        }
    }

    public void getCurrentUser(PostModel.Listener<User> callback) {
        String userId = AuthModel.instance().getCurrentUserId();
        dbCollections.getUserById(userId, callback);
    }

    public String getCurrentUserId() {
        return AuthModel.instance().getCurrentUserId();
    }

    public void getUserNameById(String authorId, PostModel.Listener<User> callback) {
         dbCollections.getUserById(authorId, callback);
    }

    public void updateUser(User user, Bitmap bitmap, IEmptyOnCompleteListener listener) {
        if (bitmap != null) {
            storage.uploadImage(bitmap, user.getUserId(), STORAGE_FILE_PATH, url -> {
                user.setAvatarUrl(url);
                dbCollections.updateUser(user, () -> {
                    listener.onComplete();
                });
            });
        } else {
            dbCollections.updateUser(user, () -> {
                listener.onComplete();
            });
        }
    }
}
