package com.example.shawarmos.fragments;

import android.content.Intent;
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

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.R;
import com.example.shawarmos.ShawarmaRecyclerAdapter;
import com.example.shawarmos.activities.LoginActivity;
import com.example.shawarmos.databinding.FragmentProfileBinding;
import com.example.shawarmos.models.Review;

import java.util.LinkedList;
import java.util.List;

public class ProfileFragment extends Fragment {

    ShawarmaRecyclerAdapter adapter;
    FragmentProfileBinding binding;

    List<Review> data;
    RecyclerView recyclerView;

//    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.profileFragmentSignOutBtn.setOnClickListener(view1 -> {
            Model.instance().signOut(new Model.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean isLoggedOutSuccessfully) {
                    if (isLoggedOutSuccessfully) {
                        redirectToLoginActivity();
                    }
                }
            });
        });

//        recyclerView = view.findViewById(R.id.profile_fragment_my_reviews_rv);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // TODO: Add progress bar
//        // TODO: take all user data
//        data = new LinkedList<>();
//
//        adapter = new ShawarmaRecyclerAdapter(getLayoutInflater(), data);
//        recyclerView.setAdapter(adapter);
//
//        adapter.setOnItemClickListener(new ShawarmaRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int pos) {
//                Log.d("TAG", "Row was clicked " + pos);
////                ReviewModel review = viewModel.getData().get(pos);
//            }
//        });

        return view;
    }

    private void redirectToLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}