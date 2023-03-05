package com.example.shawarmos.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.R;
import com.example.shawarmos.activities.FeedActivity;
import com.example.shawarmos.databinding.FragmentLoginBinding;
import com.example.shawarmos.databinding.FragmentShawarmaListBinding;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.loginFragmentBtn.setOnClickListener(view1 -> {
            String email = binding.loginFragmentUsernameEt.getText().toString();
            String password = binding.loginFragmentPasswordEt.getText().toString();

            if (!isFormValid(email, password)) {
                return;
            }

            Model.instance().login(email, password, (Boolean isConnected) -> {
                    if (isConnected) {
                        redirectToFeedActivity();
                        // TODO:  Clear here the back button stack
                    } else {
                        // TODO: Check here if the user name or password is incorrect
                        // TODO: Dialog of password or username is incorrect
                    }
            });
        });

        binding.loginFragmentRegisterBtn.setOnClickListener(view1 -> {
            NavDirections directions = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
            Navigation.findNavController(view).navigate(directions);
        });

        return view;
    }

    private boolean isFormValid(String email, String password) {
        boolean isValid = true;

        if (email.isEmpty()) {
            binding.loginFragmentUsernameEt.setError("Please enter email address");
            isValid = false;
        }

        if (password.isEmpty()) {
            binding.loginFragmentPasswordEt.setError("Please enter password");
            isValid = false;
        }

        return isValid;
    }

    private void redirectToFeedActivity() {
        Intent intent = new Intent(getContext(), FeedActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}