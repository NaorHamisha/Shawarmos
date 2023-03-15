package com.example.shawarmos.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shawarmos.R;
import com.example.shawarmos.databinding.FragmentDiyRecipeBinding;
import com.example.shawarmos.entities.Meal;
import com.example.shawarmos.models.MealModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DiyRecipeFragment extends Fragment {

    private FragmentDiyRecipeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding  = FragmentDiyRecipeBinding.inflate(inflater, container, false);

        binding.diyRecipeFragmentProgressBar.setVisibility(View.VISIBLE);
        binding.diyRecipeFragmentInstructionsTv.setMovementMethod(new ScrollingMovementMethod());

        LiveData<List<Meal>> data = MealModel.instance.searchMealByName("shawarma");
        data.observe(getActivity(), list->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                list.forEach(item->{
                    initializePage(item);
                });
                binding.diyRecipeFragmentProgressBar.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }

    private void initializePage(Meal meal) {
        binding.diyRecipeFragmentAreaTv.setText(meal.getStrArea());
        binding.diyRecipeFragmentCategoryTv.setText(meal.getStrCategory());
        binding.diyRecipeFragmentMealTitleTv.setText(meal.getStrMeal());
        binding.diyRecipeFragmentInstructionsTv.setText(meal.getStrInstructions());

        String imgUrl = meal.getStrMealThumb();

        if (imgUrl != null && imgUrl != "") {
            Picasso.get().load(imgUrl).placeholder(R.drawable.logo).into(binding.diyRecipeFragmentImg);
        }
    }
}