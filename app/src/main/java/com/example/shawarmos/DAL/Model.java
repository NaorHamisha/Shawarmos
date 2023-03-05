package com.example.shawarmos.DAL;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shawarmos.models.Review;
import com.example.shawarmos.models.UserInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {

    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private FirebaseModel firebaseModel = new FirebaseModel();
    private AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static Model instance() {
        return _instance;
    }

    private Model() {}

    public void login(String username, String password, Listener<Boolean> listener) {
        // TODO make the login to ask for the user info
        firebaseModel.login(username, password, listener);
    }

    public void register(String email, String password, String username, String imageUrl, Listener<Boolean> listener) {
        firebaseModel.registerUser(email, password, username, imageUrl, listener);
    }

    public boolean isLogged() {
        return firebaseModel.isLoggedIn();
    }


    public void signOut(Listener<Boolean> listener) {
        firebaseModel.signOutUser();
        listener.onComplete(true);
    }

    public void updateCurrentUserInfo(UserInfo updatedUserInfo) {
            // TODO save the new user info in the user
    }

    public void addReview(Review review, Listener<Void> listener) {
        firebaseModel.addReview(review, (Void)->{
            refreshShawarmaList();
            listener.onComplete(null);
        });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name, bitmap, listener);
    }

    public void refreshShawarmaList() {
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
            refreshShawarmaList();
        }

        return reviewsList;
    }

    public LiveData<List<Review>> getCurrentUserReviews() {
        // TODO: filter here all the review of the user

        LiveData<List<Review>> posts = getAllReviews();
        List<Review> myPosts = new LinkedList<>();

        MutableLiveData<List<Review>> postsToReturn = new MutableLiveData<>();
        if (posts.getValue() != null) {
            for(Review post : posts.getValue()){
//                if(post.getAuthor().equals(userUid)){
                    myPosts.add(post);
                    break;
//                }
            }
        }

        postsToReturn.setValue(myPosts);
        return postsToReturn;
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
