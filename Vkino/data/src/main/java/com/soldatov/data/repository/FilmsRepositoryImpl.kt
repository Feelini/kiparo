package com.soldatov.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.soldatov.data.api.PlaceHolderApi
import com.soldatov.data.models.profile.RegisterError
import com.soldatov.domain.models.*
import com.soldatov.domain.models.profile.*
import com.soldatov.domain.repository.FilmsRepository
import retrofit2.HttpException
import kotlin.Exception

const val FILM_HOME_MODE = "com.soldatov.vkino.data.repository.FILM_HOME_MODE"
const val FILM_SLIDER_MODE = "com.soldatov.vkino.data.repository.FILM_SLIDER_MODE"
const val FILM_SEARCH_MODE = "com.soldatov.vkino.data.repository.FILM_SEARCH_MODE"
const val APP_PREFERENCES = "PREFERENCES_SETTINGS"
const val APP_PREFERENCES_TOKEN = "PREFERENCES_TOKEN"

class FilmsRepositoryImpl(
    private val placeHolderApi: PlaceHolderApi,
    private val context: Context
) :
    FilmsRepository {

    private val lastSlider = ArrayList<FilmInfo>()
    private val homePageFilms = ArrayList<FilmInfo>()
    private val searchFilms = ArrayList<FilmInfo>()
    private val searchCountries = ArrayList<Country>()
    private val searchActors = ArrayList<Actor>()
    private val sharedPreferences =
        context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    override suspend fun getTopSliderInfo(): List<FilmInfo> {
        val topSliderInfo = placeHolderApi.getTopSliderInfo()
        lastSlider.clear()
        lastSlider.addAll(topSliderInfo.data.films.map { it.toDomain() })
        return lastSlider
    }

    override suspend fun getSimilarFilmsInfo(filmId: Long): List<FilmInfo> {
        val similarFilms = placeHolderApi.getSimilarFilmsInfo(filmId)
        lastSlider.clear()
        lastSlider.addAll(similarFilms.data.films.map { it.toDomain() })
        return lastSlider
    }

    override suspend fun getHomePageFilms(filterParams: FilterParams): FilmsList {
        val dataFilterParams = filterParams.toData()
        val homePageFilmsInfo = placeHolderApi.getHomePageFilms(
            page = dataFilterParams.page,
            actors = dataFilterParams.chosenActors,
            countries = dataFilterParams.chosenCountries,
            genres = dataFilterParams.chosenGenres,
            qualities = dataFilterParams.chosenQualities,
            categories = dataFilterParams.chosenCategories,
            years = dataFilterParams.chosenYears,
            orderBy = dataFilterParams.orderBy,
            order = dataFilterParams.order
        )
        if (filterParams.page == 1) {
            homePageFilms.clear()
            homePageFilms.addAll(homePageFilmsInfo.data.films.map { it.toDomain() })
        } else {
            homePageFilms.addAll(homePageFilmsInfo.data.films.map { it.toDomain() })
        }
        val hasMore = (homePageFilms.size != homePageFilmsInfo.data.totalRows)
        return FilmsList(homePageFilmsInfo.data.totalRows, homePageFilms, hasMore)
    }

    override suspend fun getById(filmId: Long, mode: String): FilmInfo {
        return try {
            when (mode) {
                FILM_SLIDER_MODE -> {
                    lastSlider.filter { it.filmId == filmId }[0]
                }
                FILM_HOME_MODE -> {
                    homePageFilms.filter { it.filmId == filmId }[0]
                }
                else -> {
                    searchFilms.filter { it.filmId == filmId }[0]
                }
            }
        } catch (e: Exception) {
            val filmInfo = placeHolderApi.getFilmById(filmId)
            filmInfo.data.toDomain()
        }
    }

    override suspend fun getSearchFilms(searchQuery: String, page: Int): FilmsList {
        val searchFilmsInfo = placeHolderApi.getSearchFilmsInfo(searchQuery, page)
        if (page == 1) {
            searchFilms.clear()
            searchFilms.addAll(searchFilmsInfo.data.films.map { it.toDomain() })
        } else {
            searchFilms.addAll(searchFilmsInfo.data.films.map { it.toDomain() })
        }
        val hasMore = (searchFilms.size != searchFilmsInfo.data.totalRows)
        return FilmsList(searchFilmsInfo.data.totalRows, searchFilms, hasMore)
    }

    override suspend fun getGenres(): GenresList {
        return placeHolderApi.getGenres().data.toDomain()
    }

    override suspend fun getCategories(): List<Category> {
        return placeHolderApi.getCategories().data.toDomain()
    }

    override suspend fun getYears(): Years {
        return placeHolderApi.getYears().data.toDomain()
    }

    override suspend fun getCountries(searchQuery: String, page: Int): CountriesList {
        val countries = placeHolderApi.getCountries(searchQuery, page).data.toDomain()
        if (page == 1) {
            searchCountries.clear()
            searchCountries.addAll(countries.countries)
        } else {
            searchCountries.addAll(countries.countries)
        }
        return CountriesList(countries.hasMore, searchCountries)
    }

    override suspend fun getActors(searchQuery: String, page: Int): ActorsList {
        val actors = placeHolderApi.getActors(searchQuery, page).data.toDomain()
        if (page == 1) {
            searchActors.clear()
            searchActors.addAll(actors.actors)
        } else {
            searchActors.addAll(actors.actors)
        }
        return ActorsList(actors.hasMore, searchActors)
    }

    override suspend fun getQualities(): List<Quality> {
        return placeHolderApi.getQualities().data.toDomain()
    }

    override fun getOrderByData(): List<String> {
        return OrderBy.values().map { it.orderName }
    }

    override suspend fun loginUser(loginData: LoginData): String? {
        try {
            return placeHolderApi.loginUser(loginData.toData()).data.token
        } catch (exception: HttpException) {
            if (exception.code() == 401) {
                return null
            }
        }
        return null
    }

    override fun saveUserToken(token: String) {
        sharedPreferences.edit().putString(APP_PREFERENCES_TOKEN, token).apply()
    }

    override fun getUserToken(): String {
        return sharedPreferences.getString(APP_PREFERENCES_TOKEN, "")!!
    }

    override suspend fun getUserInfo(userToken: String): UserInfoResult {
        return try {
            UserInfoResult.Success(data = placeHolderApi.getUserInfo(userToken).data.toDomain())
        } catch (exception: Exception) {
            UserInfoResult.Error(message = exception.message ?: "Error occurred")
        }
    }

    override fun quitProfile() {
        sharedPreferences.edit().putString(APP_PREFERENCES_TOKEN, null).apply()
    }

    override suspend fun registerUser(registerData: RegisterData): RegisterResult {
        return try {
            RegisterResult.Success(token = placeHolderApi.registerUser(registerData.toData()).data.token)
        } catch (exception: HttpException) {
            val gson = GsonBuilder().create()
            val raw = exception.response()?.errorBody()?.string()
            val error = gson.fromJson(raw, RegisterError::class.java)
            RegisterResult.Error(message = error.error.msg)
        }
    }

    override suspend fun updateProfile(userInfo: UserInfo, token: String): UserInfoResult {
        return try {
            UserInfoResult.Success(
                data = placeHolderApi.updateProfile(
                    userInfo.toData(),
                    token
                ).data.toDomain()
            )
        } catch (exception: HttpException){
            val gson = GsonBuilder().create()
            val raw = exception.response()?.errorBody()?.string()
            val error = gson.fromJson(raw, RegisterError::class.java)
            UserInfoResult.Error(message = error.error.msg)
        }
    }
}