package com.example.shawarmos.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealModel {

    final public static MealModel instance = new MealModel();
    final String BASE_URL = "https://www.themealdb.com/";

    private Retrofit retrofit;
    private MealApi mealApi;

    private MealModel() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mealApi = retrofit.create(MealApi.class);
    }

    public LiveData<List<Meal>> searchMealByName(String name){
        MutableLiveData<List<Meal>> data = new MutableLiveData<>();
        Call<MealApiResult> call = mealApi.searchMealByName(name);
        call.enqueue(new Callback<MealApiResult>() {
            @Override
            public void onResponse(Call<MealApiResult> call, Response<MealApiResult> response) {
                if (response.isSuccessful()) {
                    MealApiResult  res = response.body();
                    data.setValue(res.getMeals());
                } else {
                    Log.d("Tag", "response error");
                }
            }

            @Override
            public void onFailure(Call<MealApiResult> call, Throwable t) {
                Log.d("Tag", "Failed");
            }
        });
        return data;
    }

}
