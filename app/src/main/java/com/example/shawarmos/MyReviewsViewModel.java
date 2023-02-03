package com.example.shawarmos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.models.Review;

import java.util.List;

public class MyReviewsViewModel extends ViewModel {

    private LiveData<List<Review>> data = Model.instance().getCurrentUserReviews();

    public LiveData<List<Review>> getMyReviews() { return data; }
}
