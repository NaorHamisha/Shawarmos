package com.example.shawarmos.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;

import com.example.shawarmos.R;
import com.google.android.material.navigation.NavigationView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavController navController;
    DrawerLayout drawerLayout;

    ImageView profileImgImv;
    TextView displayNameTv;
    TextView emailTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

       // viewModel = new ViewModelProvider(this).get(BaseActivityViewModel.class);

        NavHost navHost = (NavHost) getSupportFragmentManager().findFragmentById(R.id.base_navhost);
        navController = navHost.getNavController();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.navigationView);
        navView.setNavigationItemSelectedListener(this);
        navView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                initUser();

                profileImgImv = findViewById(R.id.menu_img_imv);
                displayNameTv = findViewById(R.id.menu_display_name_tv);
                emailTv = findViewById(R.id.menu_email_tv);

                setUserDetails();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                if (getCurrentFocus() != null) {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        };

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();


//        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
//        navController = navHostFragment.getNavController();
//        NavigationUI.setupActionBarWithNavController(this,navController);
//
//        BottomNavigationView navView = findViewById(R.id.main_bottomNavigationView);
//        NavigationUI.setupWithNavController(navView, navController);

        // For external api
//        LiveData<List<Meal>> data = MealModel.instance.searchMealByName("shawarma");
//        data.observe(this, list->{
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                list.forEach(item->{
//                    Log.d("Tag", "got meal: " + item.getStrMealThumb());
//                });
//            }
//        });


    }
    private void initUser() {
     //   user = viewModel.getUserById(Model.instance.getCurrentUserUID()).getValue();
    }

    private void setUserDetails() {
       // if (user == null) return;

//        displayNameTv.setText(user.getDisplayName());
//        emailTv.setText(user.getEmail());
//        if(user.getImg() != null){
//            Picasso.get().load(user.getImg()).into(profileImgImv);
//        } else {
//            profileImgImv.setImageResource(R.mipmap.ic_launcher_round);
//        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Bundle args = new Bundle();

        switch (item.getItemId()) {
            case android.R.id.home:
                navController.navigateUp();
                break;
            case R.id.menu_mainFeedFragment:
                args.putBoolean("isUserReviews", false);
                navController.navigate(R.id.action_global_mainFeedFragment);
                break;
            case R.id.menu_myReviewsFragment:
                args.putBoolean("isUserReviews", true);
                navController.navigate(R.id.action_global_mainFeedFragment, args);
                break;
            case R.id.menu_sign_out:
                // Do sign out
                break;
            default:
                NavigationUI.onNavDestinationSelected(item, navController);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}