package com.soldatov.data.repository

import com.soldatov.data.api.PlaceHolderApi
import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.models.SearchData
import com.soldatov.domain.repository.FilmsRepository
import java.lang.Exception

const val FILM_HOME_MODE = "com.soldatov.vkino.data.repository.FILM_HOME_MODE"
const val FILM_SLIDER_MODE = "com.soldatov.vkino.data.repository.FILM_SLIDER_MODE"
const val FILM_SEARCH_MODE = "com.soldatov.vkino.data.repository.FILM_SEARCH_MODE"

class FilmsRepositoryImpl(private val placeHolderApi: PlaceHolderApi) : FilmsRepository {

    private lateinit var lastSlider: List<FilmInfo>
    private lateinit var homePageFilms: List<FilmInfo>
    private var searchFilms = ArrayList<FilmInfo>()

    override suspend fun getTopSliderInfo(): List<FilmInfo> {
        val topSliderInfo = placeHolderApi.getTopSliderInfo()
        lastSlider = topSliderInfo.data.films.map { it.toDomain() }
        return lastSlider
    }

    override suspend fun getSimilarFilmsInfo(filmId: Long): List<FilmInfo> {
        val similarFilms = placeHolderApi.getSimilarFilmsInfo(filmId)
        lastSlider = similarFilms.data.films.map { it.toDomain() }
        return lastSlider
    }

    override suspend fun getHomePageFilms(): List<FilmInfo> {
        val homePageFilmsInfo = placeHolderApi.getHomePageFilms()
        homePageFilms = homePageFilmsInfo.data.films.map { it.toDomain() }
        return homePageFilms
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

    override suspend fun getSearchFilms(searchQuery: String, page: Int): SearchData {
        val searchFilmsInfo = placeHolderApi.getSearchFilmsInfo(searchQuery, page)
        if (page == 1){
            searchFilms = searchFilmsInfo.data.films.map { it.toDomain() } as ArrayList<FilmInfo>
        } else {
            searchFilms.addAll(searchFilmsInfo.data.films.map { it.toDomain() })
        }
        return SearchData(searchFilmsInfo.data.totalRows, searchFilms)
    }
}