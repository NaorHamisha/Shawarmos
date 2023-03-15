package com.example.shawarmos.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.models.UserModel;
import com.example.shawarmos.R;
import com.example.shawarmos.databinding.FragmentProfileBinding;
import com.example.shawarmos.entities.User;
import com.squareup.picasso.Picasso;

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