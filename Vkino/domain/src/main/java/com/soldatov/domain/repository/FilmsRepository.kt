package com.soldatov.domain.repository

import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.models.SearchData

interface FilmsRepository {
    suspend fun getTopSliderInfo(): List<FilmInfo>
    suspend fun getHomePageFilms(): List<FilmInfo>
    suspend fun getSimilarFilmsInfo(filmId: Long): List<FilmInfo>
    suspend fun getById(filmId: Long, mode: String): FilmInfo
    suspend fun getSearchFilms(searchQuery: String, page: Int): SearchData
}