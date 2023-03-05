package com.example.shawarmos.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.viewModels.interfaces.IReviewsViewModel;
import com.example.shawarmos.models.Review;

import java.util.List;

public class FeedReviewsFragmentViewModel extends ViewModel implements IReviewsViewModel {

    private LiveData<List<Review>> data = Model.instance().getAllReviews();

    @Override
    public LiveData<List<Review>> getData() { return data; }

    @Override
    public String ActionBarTitle() {
        return "Shawarmos";
    }
}
