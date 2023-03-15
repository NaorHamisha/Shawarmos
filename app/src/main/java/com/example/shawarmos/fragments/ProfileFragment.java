package com.example.shawarmos.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.DAL.UserModel;
import com.example.shawarmos.R;
import com.example.shawarmos.ShawarmaRecyclerAdapter;
import com.example.shawarmos.activities.LoginActivity;
import com.example.shawarmos.databinding.FragmentProfileBinding;
import com.example.shawarmos.models.Review;
import com.example.shawarmos.models.User;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    private User currentUser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initializeUserDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Profile");

        binding.profileFragmentEditUserFab.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(ProfileFragmentDirections.actionMenuProfileFragmentToEditProfileFragment(currentUser));
        });

        initializeUserDetails();

        return view;
    }

    private void initializeUserDetails() {
        UserModel.instance().getCurrentUser(user -> {
            this.currentUser = user;

            binding.profileFragmentUserNameTv.setText(user.getUserName());
            binding.profileFragmentEmailTv.setText(user.getEmail());

            if (!Objects.equals(user.getAvatarUrl(), ""))
            {
                Picasso.get().load(user.getAvatarUrl()).placeholder(R.drawable.avatar).into(binding.profileFragmentImg);
            } else {
                Picasso.get().load((R.drawable.avatar)).into(binding.profileFragmentImg);
            }
        });
    }

}