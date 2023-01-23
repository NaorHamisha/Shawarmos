package com.example.shawarmos.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.R;
import com.example.shawarmos.ShawarmaListViewModel;
import com.example.shawarmos.ShawarmaRecyclerAdapter;
import com.example.shawarmos.models.ReviewModel;

import java.util.Arrays;
import java.util.List;

public class ShawarmaListFragment extends Fragment {
    ShawarmaRecyclerAdapter adapter;
    ShawarmaListViewModel viewModel;

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shawarma_list, container, false);

        recyclerView = view.findViewById(R.id.reviewsList_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        adapter = new ShawarmaRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());

        // Here i think i get the data fri
         List<ReviewModel> data = Arrays.asList(
                new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"),   new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"),   new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"),   new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"),   new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"),   new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"),   new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"),
                new ReviewModel("ehta","sdfsdfsdfsdfsdf"));

        adapter = new ShawarmaRecyclerAdapter(getLayoutInflater(), data);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ShawarmaRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
//                Student st = viewModel.getData().getValue().get(pos);
//                StudentsListFragmentDirections.ActionStudentsListFragmentToBlueFragment action = StudentsListFragmentDirections.actionStudentsListFragmentToBlueFragment(st.name);
//                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }
}