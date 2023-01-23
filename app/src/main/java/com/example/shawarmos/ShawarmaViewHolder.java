package com.example.shawarmos;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shawarmos.models.ReviewModel;

import java.util.List;

public class ShawarmaViewHolder extends RecyclerView.ViewHolder {
    TextView titleTv;
    TextView descriptionTv;
    List<ReviewModel> data;
    ImageView avatarImage;

    public ShawarmaViewHolder(@NonNull View itemView, ShawarmaRecyclerAdapter.OnItemClickListener listener, List<ReviewModel> data) {
        super(itemView);
        this.data = data;

        titleTv = itemView.findViewById(R.id.reviewlistrow_name_tv);
        descriptionTv = itemView.findViewById(R.id.reviewlistrow_id_tv);
        avatarImage = itemView.findViewById(R.id.reviewlistrow_avatar_img);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(ReviewModel rv, int pos) {
        titleTv.setText(rv.title);
        descriptionTv.setText(rv.description);
    }
}
