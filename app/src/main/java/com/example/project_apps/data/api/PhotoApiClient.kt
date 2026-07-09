package com.example.project_apps.data.api

import com.example.project_apps.data.api.PhotoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PhotoApiClient {
    private const val BASE_URL = "https://picsum.photos/v2/"

    val apiService: PhotoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotoApiService::class.java)
    }
}