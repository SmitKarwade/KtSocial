package com.example.instagram.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.instagram.modal.Photo;
import com.example.instagram.repository.Repository;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private Repository repository;
    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public MutableLiveData<List<Photo>> getViewImages(){
        return repository.getListMutableLiveData();
    }
}
