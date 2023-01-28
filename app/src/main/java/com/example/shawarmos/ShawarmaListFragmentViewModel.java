package com.example.shawarmos;

import android.widget.CheckBox;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.models.Review;

import java.util.List;

public class ShawarmaListFragmentViewModel extends ViewModel {

    private LiveData<List<Review>> data = Model.instance().getAllReviews();

    public LiveData<List<Review>> getData() { return data; }
}
