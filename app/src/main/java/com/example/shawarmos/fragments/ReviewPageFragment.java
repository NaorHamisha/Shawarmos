package com.example.shawarmos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shawarmos.R;
import com.example.shawarmos.models.Review;
import com.squareup.picasso.Picasso;


public class ReviewPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_page, container, false);

        TextView description = view.findViewById(R.id.review_page_fragment_description_tv);
        TextView title = view.findViewById(R.id.review_page_fragment_title_tv);
        TextView author = view.findViewById(R.id.review_page_fragment_author_tv);
        ImageView imgView = view.findViewById(R.id.review_page_fragment_img);
        RatingBar rank = view.findViewById(R.id.reviewlistrow_rating_rb);
//        Button editBtn = findViewById(R.id.student_details_edit_btn);

        Review review = ReviewPageFragmentArgs.fromBundle(getArguments()).getReview();

        title.setText(review.title);
        description.setText(review.description);
        author.setText("By " + review.author);
        rank.setRating((float) review.rating);

        if (review.getImageUrl() != null && !review.getImageUrl().equals("")) {
            Picasso.get().load(review.getImageUrl()).placeholder(R.drawable.avatar).into(imgView);
        }else{
            imgView.setImageResource(R.drawable.avatar);
        }

        return view;
    }
}