package com.oladimeji.lagosdevelopers.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oladimeji on 7/10/2017.
 */

public class QueryClient {
    private static final String GITHUB_API_STRING = "https://api.github.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(GITHUB_API_STRING)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
