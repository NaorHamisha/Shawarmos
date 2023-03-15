package com.example.shawarmos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.models.UserModel;
import com.example.shawarmos.R;
import com.example.shawarmos.databinding.FragmentReviewPageBinding;
import com.example.shawarmos.entities.Review;
import com.squareup.picasso.Picasso;

public class ReviewPageFragment extends Fragment {

    private FragmentReviewPageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReviewPageBinding.inflate(inflater, container, false);

        Review review = ReviewPageFragmentArgs.fromBundle(getArguments()).getReview();

        Boolean isEditable = (review.author.equals(UserModel.instance().getCurrentUserId()));

        binding.reviewPageFragmentEditPostFab.setVisibility(isEditable ? View.VISIBLE : View.GONE);

        binding.reviewPageFragmentEditPostFab.setOnClickListener(view -> {
            NavDirections directions = ReviewPageFragmentDirections.actionReviewPageFragmentToAddReviewFragment(review);
            Navigation.findNavController(view).navigate(directions);
        });

        binding.reviewPageFragmentTitleTv.setText(review.title);
        binding.reviewPageFragmentDescriptionTv.setText(review.description);
        UserModel.instance().getUserNameById(review.author, user -> {
            binding.reviewPageFragmentAuthorTv.setText("By " + user.getUserName());
        });
        binding.reviewlistrowRatingRb.setRating((float) review.rating);

        if (review.getImageUrl() != null && !review.getImageUrl().equals("")) {
            Picasso.get().load(review.getImageUrl()).placeholder(R.drawable.avatar).into(binding.reviewPageFragmentImg);
        }else{
            binding.reviewPageFragmentImg.setImageResource(R.drawable.avatar);
        }

        return binding.getRoot();
    }
}