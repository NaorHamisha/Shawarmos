package com.example.shawarmos;

import androidx.lifecycle.ViewModel;

import com.example.shawarmos.models.ReviewModel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ShawarmaListViewModel extends ViewModel {

//    private LiveData<List<ReviewModel>> data = null;
//    private LiveData<List<ReviewModel>> data = Model.instace.getAllReviews();
//
//    public LiveData<List<ReviewModel>> getData() { return data; }
    private List<ReviewModel> data = Arrays.asList(
            new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
            new ReviewModel("sdfsdfsd","hrerere"),
            new ReviewModel("ehta","sdfsdfsdfsdfsdf"));

    public List<ReviewModel> getData() { return data; }
}
