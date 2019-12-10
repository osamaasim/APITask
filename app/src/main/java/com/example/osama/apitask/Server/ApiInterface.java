package com.example.osama.apitask.Server;

import com.example.osama.apitask.Model.APIHandler;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("mostpopular/v2/mostviewed/{section}/{period}.json")
    Call<APIHandler> getNewsDetails(@Path("section") String section, @Path("period") String period,
                                    @Query("api-key") String apiKey);
}
