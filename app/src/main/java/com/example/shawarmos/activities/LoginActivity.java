package com.example.shawarmos.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.R;
import com.example.shawarmos.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    NavController navController;
    //private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.auth_nav_host);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);


//        binding = ActivityLoginBinding.inflate(getLayoutInflater());
//
//        binding.loginActivityBtn.setOnClickListener(view1-> {
//            String username = binding.loginActivityUsernameEt.getText().toString();
//            String password = binding.loginActivityPasswordEt.getText().toString();
//
//            Navigation.findNavController(view1).navigate(R.id.shawarmaListFragment);

//            Model.instance().logIn(username, password, (unused) -> {
//                Navigation.findNavController(view1).navigate(R.id.shawarmaListFragment);
//            });
  //      });
    }

}