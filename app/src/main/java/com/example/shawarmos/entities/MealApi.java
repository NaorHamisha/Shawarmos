package com.example.shawarmos.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApi {

    @GET("/api/json/v1/1/search.php")
    Call<MealApiResult> searchMealByName(@Query("s") String name);
}
