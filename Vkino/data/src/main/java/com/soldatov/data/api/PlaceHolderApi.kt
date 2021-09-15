package com.soldatov.data.api

import com.soldatov.data.models.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceHolderApi {
    @GET("films/top-slider")
    suspend fun getTopSliderInfo(): Response
    @GET("films/similar")
    suspend fun getSimilarFilmsInfo(@Query("film_id") filmId: Long): Response
}