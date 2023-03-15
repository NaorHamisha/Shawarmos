package com.example.shawarmos;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shawarmos.DAL.UserModel;
import com.example.shawarmos.models.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShawarmaViewHolder extends RecyclerView.ViewHolder {
    List<Review> data;

    TextView titleTv;
    TextView authorTv;
    RatingBar ratingRb;
    ImageView avatarImage;

    public ShawarmaViewHolder(@NonNull View itemView, ShawarmaRecyclerAdapter.OnItemClickListener listener, List<Review> data) {
        super(itemView);
        this.data = data;

        titleTv = itemView.findViewById(R.id.reviewlistrow_name_tv);
        authorTv = itemView.findViewById(R.id.reviewlistrow_author_tv);
        avatarImage = itemView.findViewById(R.id.reviewlistrow_avatar_img);
        ratingRb = itemView.findViewById(R.id.reviewlistrow_rating_rb);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(Review review, int pos) {
        titleTv.setText(review.title);

        UserModel.instance().getUserNameById(review.author, user -> {
            authorTv.setText("By " + user.getUserName());
        });

        ratingRb.setRating((float)review.rating);

        if (review.getImageUrl() != null && !review.getImageUrl().equals("")) {
            Picasso.get().load(review.getImageUrl()).placeholder(R.drawable.logo).into(avatarImage);
        }else{
            avatarImage.setImageResource(R.drawable.logo);
        }
    }
}
