package com.example.shawarmos.DAL;

import android.graphics.Bitmap;

import com.example.shawarmos.models.ReviewModel;

public class Model {

    private static final Model _instance = new Model();

    private FirebaseModel firebaseModel = new FirebaseModel();

    public static Model instance(){
        return _instance;
    }

    private Model() {}

    public void addReview(ReviewModel review, Listener<Void> listener) {
        firebaseModel.addReview(review, (Void)->{
            refreshAllStudents();
            listener.onComplete(null);
        });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name, bitmap, listener);
    }

    public void refreshAllStudents() { }

    public interface Listener<T>{
        void onComplete(T data);
    }

    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }
}
