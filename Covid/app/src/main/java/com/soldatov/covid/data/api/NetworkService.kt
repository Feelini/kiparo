package com.soldatov.covid.data.api

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class NetworkService {

    private val BASE_URL = "https://api.kiparo.com/"
    private var retrofit: Retrofit? = null

    companion object{
        private var instance: NetworkService? = null

        fun getInstance(): NetworkService? {
            if (instance == null) {
                instance = NetworkService()
            }
            return instance
        }
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getJSONApi(): JSONPlaceHolderApi? {
        return retrofit!!.create(JSONPlaceHolderApi::class.java)
    }
}