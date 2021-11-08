package com.soldatov.domain.repository

import com.soldatov.domain.models.*
import com.soldatov.domain.models.profile.*

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
    fun saveUserToken(token: String)
    fun isLogInUser(): Boolean
    suspend fun getUserInfo(): UserInfoResult
    fun quitProfile()
    suspend fun registerUser(registerData: RegisterData): RegisterResult
    suspend fun updateProfile(userInfo: UserInfo): UserInfoResult
}