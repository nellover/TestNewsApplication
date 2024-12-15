package com.example.tp_news_apptest.ApiRetr;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("v2/everything")
    Call<NewsResponse> getNews(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );
}