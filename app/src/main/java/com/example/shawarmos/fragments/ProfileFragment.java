package com.example.shawarmos.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.R;
import com.example.shawarmos.ShawarmaRecyclerAdapter;
import com.example.shawarmos.databinding.FragmentProfileBinding;
import com.example.shawarmos.models.Review;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ProfileFragment extends Fragment {

    ShawarmaRecyclerAdapter adapter;
    FragmentProfileBinding binding;

    List<Review> data;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        recyclerView = view.findViewById(R.id.profile_fragment_my_reviews_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO: take all user data
        data = new LinkedList<>();

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