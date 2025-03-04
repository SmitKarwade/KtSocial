package com.example.ktinsta.reqinterface

import com.example.ktinsta.modal.PexelResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ImageService {
    @GET("v1/popular")
    suspend fun getImages(
        @Header("Authorization") api_key: String?,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): PexelResponse
}
