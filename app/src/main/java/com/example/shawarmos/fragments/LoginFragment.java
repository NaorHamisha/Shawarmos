package com.example.shawarmos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.R;
import com.example.shawarmos.databinding.FragmentLoginBinding;
import com.example.shawarmos.databinding.FragmentShawarmaListBinding;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.loginFragmentBtn.setOnClickListener(view1 -> {
            boolean check = Model.instance().isLogged();

            String username = binding.loginFragmentUsernameEt.getText().toString();
            String password = binding.loginFragmentPasswordEt.getText().toString();

            // TODO: Check here if the user name or password is incorrect

            Model.instance().login(username, password, (Boolean isConnected) -> {
                    if (isConnected) {
                        NavDirections directions = LoginFragmentDirections.actionLoginFragmentToNavGraph();
                        // TODO:  Clear here the back button stack
                        Navigation.findNavController(view).navigate(directions);
                    } else {
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
}