package com.example.ktinsta.repository

import com.example.ktinsta.constants.Constants
import com.example.ktinsta.dataorexception.DataOrException
import com.example.ktinsta.modal.PexelResponse
import com.example.ktinsta.reqinterface.ImageService
import javax.inject.Inject

class Repository @Inject constructor(private val imageService: ImageService) {
    suspend fun images(pageNo: Int, perPage: Int): DataOrException<PexelResponse, Boolean, Exception> {
        val result = DataOrException<PexelResponse, Boolean, Exception>(loading = true)
        try {
            val response = imageService.getImages(Constants.API_KEY, pageNo, perPage)
            result.data = response
            result.loading = false
        } catch (e: Exception) {
            result.e = e
            result.loading = false
        }
        return result
    }
}

