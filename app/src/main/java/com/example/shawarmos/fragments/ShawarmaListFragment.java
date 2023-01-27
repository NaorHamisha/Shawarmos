package com.example.shawarmos.fragments;

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
import com.example.shawarmos.ShawarmaListViewModel;
import com.example.shawarmos.ShawarmaRecyclerAdapter;
import com.example.shawarmos.databinding.FragmentShawarmaListBinding;
import com.example.shawarmos.models.ReviewModel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ShawarmaListFragment extends Fragment {

    private FragmentShawarmaListBinding binding;
    private ShawarmaRecyclerAdapter adapter;
    private ShawarmaListViewModel viewModel;

    // TODO connect it to an db class
    List<ReviewModel> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShawarmaListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.reviewsListRv.setHasFixedSize(true);
        binding.reviewsListRv.setLayoutManager(new LinearLayoutManager(getContext()));

        //        adapter = new ShawarmaRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());

        data = Arrays.asList(
                new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"), new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"), new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"), new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"), new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"), new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"), new ReviewModel("dsfsdfsdf","sdfsdfsdf"),
                new ReviewModel("sdfsdfsd","hrerere"),
                new ReviewModel("ehta","sdfsdfsdfsdfsdf"));

        adapter = new ShawarmaRecyclerAdapter(getLayoutInflater(), data);
        binding.reviewsListRv.setAdapter(adapter);

        adapter.setOnItemClickListener(new ShawarmaRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
//                ReviewModel review = viewModel.getData().get(pos);
                ReviewModel review = data.get(pos);

                NavDirections directions = ShawarmaListFragmentDirections.actionShawarmaListFragmentToReviewPageFragment(review);

                Navigation.findNavController(view).navigate(directions);
            }
        });

        return view;
    }
}