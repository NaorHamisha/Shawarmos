package com.example.shawarmos.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.R;
import com.example.shawarmos.ShawarmaRecyclerAdapter;
import com.example.shawarmos.models.ReviewModel;

import java.util.Arrays;
import java.util.List;

public class ProfileFragment extends Fragment {

    ShawarmaRecyclerAdapter adapter;
    List<ReviewModel> data;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        recyclerView = view.findViewById(R.id.profile_fragment_my_reviews_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        data = Arrays.asList(
                new ReviewModel("Naor","What the fuck"),
                new ReviewModel("May koren","You hate her"),
                new ReviewModel("Naor","What the fuck"),
                new ReviewModel("May koren","You hate her"),
                new ReviewModel("Naor","What the fuck"),
                new ReviewModel("May koren","You hate her"),
                new ReviewModel("Test","Fuck her"));

        adapter = new ShawarmaRecyclerAdapter(getLayoutInflater(), data);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ShawarmaRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
//                ReviewModel review = viewModel.getData().get(pos);
            }
        });

        return view;
    }
}