package com.soldatov.covid.data.api

import com.soldatov.covid.domain.models.CovidInfo
import retrofit2.http.GET
import retrofit2.http.Headers

private const val HEADER_VARY1 = "Vary1: dfgfdsg4358ou9h48ihkdsjfhds"

interface JSONPlaceHolderApi {
    @Headers(HEADER_VARY1)
    @GET("covid-19")
    suspend fun getCovidInfo(): CovidInfo
}