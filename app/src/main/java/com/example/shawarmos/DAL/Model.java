package com.example.shawarmos.DAL;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shawarmos.models.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private FirebaseModel firebaseModel = new FirebaseModel();
    private AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static Model instance(){
        return _instance;
    }
    private Model(){
    }
    public void addReview(Review review, Listener<Void> listener) {
        firebaseModel.addReview(review, (Void)->{
            refreshAllStudents();
            listener.onComplete(null);
        });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name, bitmap, listener);
    }

    public void refreshAllStudents() {
        EventReviewsListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = Review.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllReviewsSince(localLastUpdate,list-> executor.execute(()->{
            Log.d("TAG", " firebase return : " + list.size());
            Long time = localLastUpdate;
            for(Review review:list){
                // insert new records into ROOM
                localDb.reviewDao().insertAll(review);
                if (time < review.getLastUpdated()){
                    time = review.getLastUpdated();
                }
            }

            // update local last update
            Review.setLocalLastUpdate(time);
            EventReviewsListLoadingState.postValue(LoadingState.NOT_LOADING);
        }));
    }
    private LiveData<List<Review>> reviewsList;

    public LiveData<List<Review>> getAllReviews() {
        if(reviewsList == null){
            reviewsList = localDb.reviewDao().getAll();
            refreshAllStudents();
        }

        return reviewsList;
    }

    public interface Listener<T>{
        void onComplete(T data);
    }

    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }
    final public MutableLiveData<LoadingState> EventReviewsListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);
}
