package com.soldatov.domain.repository

import com.soldatov.domain.models.*
import com.soldatov.domain.models.profile.LoginData

interface FilmsRepository {
    suspend fun getTopSliderInfo(): List<FilmInfo>
    suspend fun getHomePageFilms(filterParams: FilterParams): FilmsList
    suspend fun getSimilarFilmsInfo(filmId: Long): List<FilmInfo>
    suspend fun getById(filmId: Long, mode: String): FilmInfo
    suspend fun getSearchFilms(searchQuery: String, page: Int): FilmsList
    suspend fun getGenres(): GenresList
    suspend fun getCategories(): List<Category>
    suspend fun getYears(): Years
    suspend fun getCountries(searchQuery: String, page: Int): CountriesList
    suspend fun getActors(searchQuery: String, page: Int): ActorsList
    suspend fun getQualities(): List<Quality>
    fun getOrderByData(): List<String>
    suspend fun loginUser(loginData: LoginData): String?
}