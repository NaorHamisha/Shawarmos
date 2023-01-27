package com.example.shawarmos.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealModel {

    final public static MealModel instance = new MealModel();
    final String BASE_URL = "https://www.themealdb.com/";
    Retrofit retrofit;
    MealApi mealApi;

    private MealModel() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mealApi = retrofit.create(MealApi.class);


        Call<MealApiResult> call = mealApi.searchMealByName("shawarma");
        call.enqueue(new Callback<MealApiResult>() {
            @Override
            public void onResponse(Call<MealApiResult> call, Response<MealApiResult> response) {
                
            }

            @Override
            public void onFailure(Call<MealApiResult> call, Throwable t) {

            }
        });
    }
}
