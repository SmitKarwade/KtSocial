package com.example.instagram.reqinterface;

import com.example.instagram.modal.PexelResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ImageService {

    @GET("v1/popular")
    Call<PexelResponse> getImages(@Header("Authorization") String api_key,
                                  @Query("page") int page,
                                  @Query("per_page") int per_page);
}
