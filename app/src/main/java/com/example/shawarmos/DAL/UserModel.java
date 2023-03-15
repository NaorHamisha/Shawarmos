package com.example.shawarmos.DAL;

import static android.provider.Settings.System.getString;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.shawarmos.DAL.Interfaces.IEmptyOnCompleteListener;
import com.example.shawarmos.DAL.firebase.FireBaseDbCollections;
import com.example.shawarmos.DAL.firebase.FireBaseStorage;
import com.example.shawarmos.R;
import com.example.shawarmos.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

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

    public void getCurrentUser(Model.Listener<User> callback) {
        String userId = AuthModel.instance().getCurrentUserId();
        dbCollections.getUserById(userId, callback);
    }

    public String getCurrentUserId() {
        return AuthModel.instance().getCurrentUserId();
    }

    public void getUserNameById(String authorId, Model.Listener<User> callback) {
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
