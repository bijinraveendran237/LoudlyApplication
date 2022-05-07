package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/repositories?")
        // API's endpoints

    Call<List<UserListResponse>> getUsersList(@Query("q") String tetris, @Query("page") int page, @Query("per_page") int pagesize);

// UserListResponse is POJO class to get the data from API, we use List<UserListResponse> in callback because the data in our API is starting from JSONArray
}