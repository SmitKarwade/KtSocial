package com.example.instagram.retrofit;

import com.example.instagram.reqinterface.ImageService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static Retrofit retrofitInstance = null;

    public static ImageService getImageService(){
        if (retrofitInstance == null){
            retrofitInstance = new retrofit2.Retrofit.Builder()
                    .baseUrl("https://api.pexels.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance.create(ImageService.class);
    }
}
