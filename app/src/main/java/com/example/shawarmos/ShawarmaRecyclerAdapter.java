package com.example.shawarmos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shawarmos.models.ReviewModel;

import java.util.List;

public class ShawarmaRecyclerAdapter extends RecyclerView.Adapter<ShawarmaViewHolder> {
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<ReviewModel> data;

    public void setData(List<ReviewModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public ShawarmaRecyclerAdapter(LayoutInflater inflater, List<ReviewModel> data) {
        this.inflater = inflater;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShawarmaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.review_list_row, parent, false);
        return new ShawarmaViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull ShawarmaViewHolder holder, int position) {
        ReviewModel rv = data.get(position);
        holder.bind(rv, position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}
