package com.nicootech.nytmaster.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nicootech.nytmaster.util.Constants.BASE_URL;

public class NYTimesClient {
    private final NYTimesService nyTimesService;
    private static final NYTimesClient INSTANCE = new NYTimesClient();

    private NYTimesClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nyTimesService = retrofit.create(NYTimesService.class);
    }

    public static NYTimesClient getInstance() {
        return INSTANCE;
    }

    public NYTimesService getNYTimesService() {
        return nyTimesService;
    }
}
