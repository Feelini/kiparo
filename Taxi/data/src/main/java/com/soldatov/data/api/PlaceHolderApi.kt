package com.soldatov.data.api

import com.soldatov.data.models.TaxiInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceHolderApi {
    @GET("static/car/")
    suspend fun getTaxiInfo(
        @Query("p1Lat") p1lat: String,
        @Query("p1Lon") p1Lon: String,
        @Query("p2Lat") p2Lat: String,
        @Query("p2Lon") p2Lon: String
    ): TaxiInfo
}