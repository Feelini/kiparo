package com.soldatov.data.api

import com.soldatov.data.models.Response
import retrofit2.http.GET

interface PlaceHolderApi {
    @GET("films/top-slider")
    suspend fun getTopSliderInfo(): Response
}