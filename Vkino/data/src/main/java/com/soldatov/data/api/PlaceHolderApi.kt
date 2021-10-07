package com.soldatov.data.api

import com.soldatov.data.models.FilmResponse
import com.soldatov.data.models.HomePageFilmsResponse
import com.soldatov.data.models.SliderResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceHolderApi {
    @GET("films/top-slider")
    suspend fun getTopSliderInfo(): SliderResponse
    @GET("film/{filmId}")
    suspend fun getFilmById(@Path("filmId") filmId: Long): FilmResponse
    @GET("films/filter")
    suspend fun getHomePageFilms(): HomePageFilmsResponse
    @GET("films/similar")
    suspend fun getSimilarFilmsInfo(@Query("film_id") filmId: Long): SliderResponse
}