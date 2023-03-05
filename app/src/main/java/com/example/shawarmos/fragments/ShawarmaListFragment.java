package com.example.shawarmos.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.viewModels.interfaces.IReviewsViewModel;
import com.example.shawarmos.viewModels.MyReviewsViewModel;
import com.example.shawarmos.viewModels.FeedReviewsFragmentViewModel;
import com.example.shawarmos.ShawarmaRecyclerAdapter;
import com.example.shawarmos.databinding.FragmentShawarmaListBinding;
import com.example.shawarmos.models.Review;

public class ShawarmaListFragment extends Fragment {

    private Boolean isUserReviews;
    private FragmentShawarmaListBinding binding;
    private ShawarmaRecyclerAdapter adapter;
    private IReviewsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShawarmaListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        isUserReviews = ShawarmaListFragmentArgs.fromBundle(getArguments()).getIsUserReviews();
        viewModel = (isUserReviews) ? new MyReviewsViewModel() : new FeedReviewsFragmentViewModel();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(viewModel.ActionBarTitle());

        binding.reviewsListRv.setHasFixedSize(true);
        binding.reviewsListRv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ShawarmaRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());

        binding.reviewsListRv.setAdapter(adapter);

        adapter.setOnItemClickListener(new ShawarmaRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Review review =  viewModel.getData().getValue().get(pos);
                NavDirections directions = ShawarmaListFragmentDirections.actionShawarmaListFragmentToReviewPageFragment(review);

                Navigation.findNavController(view).navigate(directions);
            }
        });

        Model.instance().EventReviewsListLoadingState.observe(getViewLifecycleOwner(),status->{
            binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        binding.feedFragmentAddPostFab.setOnClickListener(view1 -> {
            NavDirections directions = ShawarmaListFragmentDirections.actionMenuMainFeedFragmentToMenuAddReviewFragment(null);
            Navigation.findNavController(view1).navigate(directions);
        });

        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            adapter.setData(list);
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            reloadData();
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = (isUserReviews != null && isUserReviews) ? new ViewModelProvider(this).get(MyReviewsViewModel.class) :
                                      new ViewModelProvider(this).get(FeedReviewsFragmentViewModel.class);
    }

    void reloadData() {
        Model.instance().refreshShawarmaList();
        // TODO check if i should refresh the list even if its only my post
    }
}