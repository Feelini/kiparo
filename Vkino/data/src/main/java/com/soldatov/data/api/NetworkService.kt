package com.soldatov.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private const val BASE_URL = "https://vkino.vnetby.net/api/v1/"

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: PlaceHolderApi by lazy {
        getRetrofit().create(PlaceHolderApi::class.java)
    }
}