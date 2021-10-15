package com.soldatov.data.api

import com.soldatov.data.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceHolderApi {
    @GET("films/top-slider")
    suspend fun getTopSliderInfo(): SliderResponse

    @GET("film/{filmId}")
    suspend fun getFilmById(@Path("filmId") filmId: Long): FilmResponse

    @GET("films/filter")
    suspend fun getHomePageFilms(@Query("page") page: Int): HomePageFilmsResponse

    @GET("films/similar")
    suspend fun getSimilarFilmsInfo(@Query("film_id") filmId: Long): SliderResponse

    @GET("films/filter")
    suspend fun getSearchFilmsInfo(
        @Query("search") searchQuery: String,
        @Query("page") page: Int
    ): SearchResponse

    @GET("filter/genres")
    suspend fun getGenres(): GenresResponse

    @GET("filter/year")
    suspend fun getYears(): YearResponse
}