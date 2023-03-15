package com.example.shawarmos.models;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shawarmos.models.listeners.IEmptyOnCompleteListener;
import com.example.shawarmos.models.listeners.IImageOnCompleteListener;
import com.example.shawarmos.entities.Review;

import java.util.List;

public class PostModel extends BaseModel {

    private static final String STORAGE_FILE_PATH = "posts_images/";

    private static final PostModel _instance = new PostModel();

    private AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    private LiveData<List<Review>> reviewsList;
    private LiveData<List<Review>> userReviewsList;
    final public MutableLiveData<LoadingState> EventReviewsListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    public static PostModel instance() {
        return _instance;
    }

    private PostModel() {}

    public void addReview(Review review, IEmptyOnCompleteListener listener) {
        dbCollections.addReview(review, () -> {
            refreshShawarmaList();
            listener.onComplete();
        });
    }

    public void uploadImage(String name, Bitmap bitmap, IImageOnCompleteListener listener) {
        storage.uploadImage(bitmap, name, STORAGE_FILE_PATH, listener);
    }

    public void refreshShawarmaList() {
        EventReviewsListLoadingState.setValue(LoadingState.LOADING);
        Long localLastUpdate = Review.getLocalLastUpdate();
        dbCollections.getAllReviewsSince(localLastUpdate,list-> executor.execute(()->{
            Long time = localLastUpdate;
            for(Review review:list){
                localDb.reviewDao().insertAll(review);
                if (time < review.getLastUpdated()){
                    time = review.getLastUpdated();
                }
            }
            Review.setLocalLastUpdate(time);
            EventReviewsListLoadingState.postValue(LoadingState.NOT_LOADING);
        }));
    }

    public LiveData<List<Review>> getAllReviews() {
        if(reviewsList == null){
            reviewsList = localDb.reviewDao().getAll();
            refreshShawarmaList();
        }

        return reviewsList;
    }

    public LiveData<List<Review>> getCurrentUserReviews() {
        String id = UserModel.instance().getCurrentUserId();
        if(userReviewsList == null){
            userReviewsList = localDb.reviewDao().getAllReviewsByAuthor(id);
            refreshShawarmaList();
        }

        return userReviewsList;
    }
}
