package com.soldatov.data.api

import com.soldatov.data.models.film.FilmResponse
import com.soldatov.data.models.film.SliderResponse
import com.soldatov.data.models.filter.*
import com.soldatov.data.models.home.HomePageFilmsResponse
import com.soldatov.data.models.profile.LoginResponse
import com.soldatov.data.models.profile.LoginUser
import com.soldatov.data.models.search.SearchResponse
import retrofit2.http.*

interface PlaceHolderApi {
    @GET("films/top-slider")
    suspend fun getTopSliderInfo(): SliderResponse

    @GET("film/{filmId}")
    suspend fun getFilmById(@Path("filmId") filmId: Long): FilmResponse

    @GET("films/filter")
    suspend fun getHomePageFilms(
        @Query("page") page: Int,
        @Query("actor") actors: String?,
        @Query("country") countries: String?,
        @Query("genre") genres: String?,
        @Query("quality") qualities: String?,
        @Query("cat_id") categories: String?,
        @Query("year") years: String?,
        @Query("orderby") orderBy: String,
        @Query("order") order: String
    ): HomePageFilmsResponse

    @GET("films/similar")
    suspend fun getSimilarFilmsInfo(@Query("film_id") filmId: Long): SliderResponse

    @GET("films/filter")
    suspend fun getSearchFilmsInfo(
        @Query("search") searchQuery: String,
        @Query("page") page: Int
    ): SearchResponse

    @GET("filter/categories")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter/genres")
    suspend fun getGenres(): GenresResponse

    @GET("filter/year")
    suspend fun getYears(): YearResponse

    @GET("filter/countries")
    suspend fun getCountries(
        @Query("search") searchQuery: String,
        @Query("page") page: Int
    ): CountiesResponse

    @GET("filter/actors")
    suspend fun getActors(
        @Query("search") searchQuery: String,
        @Query("page") page: Int
    ): ActorsResponse

    @GET("filter/qualities")
    suspend fun getQualities(): QualitiesResponse

    @POST("user/login")
    suspend fun loginUser(@Body loginUser: LoginUser): LoginResponse
}