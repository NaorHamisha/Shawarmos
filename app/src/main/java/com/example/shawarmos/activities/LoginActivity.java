package com.example.shawarmos.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.shawarmos.R;

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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(super.onOptionsItemSelected(item)) return true;

        if (item.getItemId() == android.R.id.home) {
            navController.navigateUp();
            return true;
        } else {
            NavigationUI.onNavDestinationSelected(item, navController);
            return false;
        }
    }

}