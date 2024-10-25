package com.example.instagram.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.instagram.R;
import com.example.instagram.modal.PexelResponse;
import com.example.instagram.modal.Photo;
import com.example.instagram.reqinterface.ImageService;
import com.example.instagram.retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private Application application;

    private MutableLiveData<List<Photo>> listMutableLiveData = new MutableLiveData<>();
    private ArrayList<Photo> photoList = new ArrayList<>();

    public Repository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Photo>> getListMutableLiveData(){
        ImageService imageService = RetrofitInstance.getImageService();

        Call<PexelResponse> call = imageService.getImages(application.getString(R.string.API_KEY), 11, 10);
        call.enqueue(new Callback<PexelResponse>() {
            @Override
            public void onResponse(Call<PexelResponse> call, Response<PexelResponse> response) {
                PexelResponse response1 = response.body();

                if (response1 != null && response1.getPhotos() != null){
                    photoList = (ArrayList<Photo>) response1.getPhotos();
                    listMutableLiveData.setValue(photoList);
                }
            }

            @Override
            public void onFailure(Call<PexelResponse> call, Throwable throwable) {
                Toast.makeText(application, "Error" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return listMutableLiveData;
    }
}
