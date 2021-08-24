package com.soldatov.covid.data.api

import com.soldatov.covid.domain.models.CovidInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface JSONPlaceHolderApi {
    @Headers("Vary1: dfgfdsg4358ou9h48ihkdsjfhds")
    @GET("covid-19")
    fun getCovidInfo(): Call<CovidInfo>
}