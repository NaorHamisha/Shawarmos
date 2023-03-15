package com.example.shawarmos.viewModels.interfaces;

import androidx.lifecycle.LiveData;

import com.example.shawarmos.entities.Review;

import java.util.List;

public interface IReviewsViewModel {
    LiveData<List<Review>> getData();

    String ActionBarTitle();
}
