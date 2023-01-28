package com.example.shawarmos.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.shawarmos.R;
 import com.example.shawarmos.models.Meal;
import com.example.shawarmos.models.MealModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
     NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Log in before the app is opening

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this,navController);

        BottomNavigationView navView = findViewById(R.id.main_bottomNavigationView);
        NavigationUI.setupWithNavController(navView, navController);

        LiveData<List<Meal>> data = MealModel.instance.searchMealByName("shawarma");
        data.observe(this, list->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                list.forEach(item->{
                    Log.d("Tag", "got meal: " + item.getStrMealThumb());
                });
            }
        });
    }
}