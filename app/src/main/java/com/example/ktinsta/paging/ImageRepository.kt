package com.example.ktinsta.paging

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.ktinsta.modal.Photo
import com.example.ktinsta.reqinterface.ImageService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepository @Inject constructor(private val apiService: ImageService) {
    fun getMovies(): Flow<PagingData<Photo>> {
        Log.d("ImageRepository", "getMovies called")
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(apiService) }
        ).flow
    }
}
