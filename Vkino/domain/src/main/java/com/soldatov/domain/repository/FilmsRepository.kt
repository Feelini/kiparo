package com.soldatov.domain.repository

import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.models.FilmsList
import com.soldatov.domain.models.GenresList
import com.soldatov.domain.models.Years

interface FilmsRepository {
    suspend fun getTopSliderInfo(): List<FilmInfo>
    suspend fun getHomePageFilms(page: Int): FilmsList
    suspend fun getSimilarFilmsInfo(filmId: Long): List<FilmInfo>
    suspend fun getById(filmId: Long, mode: String): FilmInfo
    suspend fun getSearchFilms(searchQuery: String, page: Int): FilmsList
    suspend fun getGenres(): GenresList
    suspend fun getYears(): Years
}