package com.nicootech.nytmaster.network;

import com.nicootech.nytmaster.models.ArticlesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.nicootech.nytmaster.util.Constants.API_KEY;

public interface NYTimesService {

    @GET("svc/search/v2/articlesearch.json?api-key="+API_KEY)
    Call<ArticlesResponse> getArticles(@Query("page") int page, @Query("q") String query);

}
