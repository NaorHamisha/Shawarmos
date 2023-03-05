package com.example.shawarmos.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.R;
import com.example.shawarmos.activities.FeedActivity;
import com.example.shawarmos.databinding.FragmentRegisterBinding;
import com.example.shawarmos.models.UserInfo;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.registerFragmentRegisterBtn.setOnClickListener(view1 -> {

            // TODO Meanwhile show an progress bar

            String password = binding.registerFragmentPasswordEt.getText().toString();
            String username = binding.registerFragmentUsernameEt.getText().toString();
            String email = binding.registerFragmentUsernameEt.getText().toString();;
            String imageUrl = "";

            Model.instance().register(email, password, username, imageUrl, new Model.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean isRegistered) {
                    if (isRegistered) {
                        // TODO: clean the stack of the return button
                        redirectToFeedActivity();
                    } else {

                    }
                }
            });
        });

        return view;
    }

    private void redirectToFeedActivity() {
        Intent intent = new Intent(getContext(), FeedActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}