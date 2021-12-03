package com.soldatov.data.api

import com.soldatov.data.models.favorite.FavoriteRequest
import com.soldatov.data.models.favorite.FavoriteResponse
import com.soldatov.data.models.favorite.AddFavoriteResponse
import com.soldatov.data.models.film.FilmResponse
import com.soldatov.data.models.film.SliderResponse
import com.soldatov.data.models.filter.*
import com.soldatov.data.models.home.HomePageFilmsResponse
import com.soldatov.data.models.profile.*
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

    @GET("user/profile")
    suspend fun getUserInfo(@Header("User-Token") token: String): UserInfoResponse

    @POST("user/register")
    suspend fun registerUser(@Body registerUser: RegisterRequest): LoginResponse

    @PUT("user/profile")
    suspend fun updateProfile(
        @Body profile: Profile,
        @Header("User-Token") token: String
    ): UserInfoResponse

    @GET("user/fav-cats")
    suspend fun getFavCats(@Header("User-Token") token: String): FavCatsResponse

    @GET("user/favorite")
    suspend fun getFavByCat(
        @Header("User-Token") token: String,
        @Query("cat_id") catId: Int,
        @Query("page") page: Int
    ): FavoriteResponse

    @PUT("user/favorite")
    suspend fun addFavorite(
        @Header("User-Token") token: String,
        @Body films: FavoriteRequest
    ): AddFavoriteResponse
}