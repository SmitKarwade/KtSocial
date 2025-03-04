package com.example.ktinsta.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktinsta.dataorexception.DataOrException
import com.example.ktinsta.modal.PexelResponse
import com.example.ktinsta.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val imageRepo: Repository) : ViewModel() {
    private val _data = MutableLiveData<DataOrException<PexelResponse, Boolean, Exception>>()
    val data: LiveData<DataOrException<PexelResponse, Boolean, Exception>> get() = _data

    fun getPostedImages(pageNo: Int, perPage: Int) {
        viewModelScope.launch {
            _data.postValue(imageRepo.images(pageNo, perPage))
        }
    }
}

