package com.example.ktinsta.di

import com.example.ktinsta.constants.Constants
import com.example.ktinsta.reqinterface.ImageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // <-- Ensure correct Hilt component
object ProviderClass {
    @Singleton
    @Provides
    fun provideApi(): ImageService {
        return Retrofit.Builder()
            .baseUrl(Constants.Base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageService::class.java)
    }
}
