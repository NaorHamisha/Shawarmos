package com.example.shawarmos.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shawarmos.models.AuthModel;
import com.example.shawarmos.activities.FeedActivity;
import com.example.shawarmos.databinding.FragmentLoginBinding;

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
        binding.loginFragmentProgressBar.setVisibility(View.GONE);

        binding.loginFragmentBtn.setOnClickListener(view1 -> {
            String email = binding.loginFragmentEmailEt.getText().toString();
            String password = binding.loginFragmentPasswordEt.getText().toString();

            if (!isFormValid(email, password)) {
                return;
            }

            binding.loginFragmentProgressBar.setVisibility(View.VISIBLE);

            AuthModel.instance().login(email, password, (id, ex) -> {
                    if (id != null) {
                        redirectToFeedActivity();
                    } else {
                        Toast.makeText(getContext(), "Authentication failed: " + ex.getMessage(),
                                Toast.LENGTH_LONG).show();
                        binding.loginFragmentProgressBar.setVisibility(View.GONE);
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
            binding.loginFragmentEmailEt.setError("Please enter email address");
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