package com.oladimeji.lagosdevelopers.Rest;

import com.oladimeji.lagosdevelopers.Profile.DevelopersProfile;
import com.oladimeji.lagosdevelopers.Profile.JSON;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Oladimeji on 7/10/2017.
 */

public interface Github{

    @GET("https://api.github.com/search/users?q=location:lagos+language:java")
    Call<JSON> getJSONResponse();

    @GET("https://api.github.com/users/{user}")
    Call<DevelopersProfile> getDeveloperProfile(@Path("user") String developerName);

}