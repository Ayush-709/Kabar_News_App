package com.template.kabar.SupportFiles;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://newsapi.org/v2/";
    String API_KEY = "3597362f1f194152ae95d797918c081d";

    @GET("top-headlines")
    Call<NewsItemsModel> getAllTopHeadlines(
            @Query("q") String query,
            @Query("from") String fromDate,
            @Query("sortBy") String sortBy,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );
}
