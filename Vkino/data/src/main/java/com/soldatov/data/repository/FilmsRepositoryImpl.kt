package com.soldatov.data.repository

import com.soldatov.data.api.PlaceHolderApi
import com.soldatov.domain.models.*
import com.soldatov.domain.repository.FilmsRepository
import java.lang.Exception

const val FILM_HOME_MODE = "com.soldatov.vkino.data.repository.FILM_HOME_MODE"
const val FILM_SLIDER_MODE = "com.soldatov.vkino.data.repository.FILM_SLIDER_MODE"
const val FILM_SEARCH_MODE = "com.soldatov.vkino.data.repository.FILM_SEARCH_MODE"

class FilmsRepositoryImpl(private val placeHolderApi: PlaceHolderApi) : FilmsRepository {

    private val lastSlider = ArrayList<FilmInfo>()
    private val homePageFilms = ArrayList<FilmInfo>()
    private val searchFilms = ArrayList<FilmInfo>()
    private val searchCountries = ArrayList<Country>()

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

    override suspend fun getHomePageFilms(page: Int): FilmsList {
        val homePageFilmsInfo = placeHolderApi.getHomePageFilms(page)
        if (page == 1){
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
        } catch (e: Exception){
            val filmInfo = placeHolderApi.getFilmById(filmId)
            filmInfo.data.toDomain()
        }
    }

    override suspend fun getSearchFilms(searchQuery: String, page: Int): FilmsList {
        val searchFilmsInfo = placeHolderApi.getSearchFilmsInfo(searchQuery, page)
        if (page == 1){
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
        if (page == 1){
            searchCountries.clear()
            searchCountries.addAll(countries.countries)
        } else {
            searchCountries.addAll(countries.countries)
        }
        return CountriesList(countries.hasMore, searchCountries)
    }
}