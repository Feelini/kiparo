package com.soldatov.covid.data.api

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object NetworkService {

    private const val BASE_URL = "https://api.kiparo.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: JSONPlaceHolderApi by lazy {
        getRetrofit().create(JSONPlaceHolderApi::class.java)
    }
}