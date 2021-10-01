package com.soldatov.domain.repository

import com.soldatov.domain.models.FilmSliderInfo

interface FilmsRepository {
    suspend fun getTopSliderInfo(): List<FilmSliderInfo>
    suspend fun getHomePageFilms(): List<FilmSliderInfo>
    suspend fun getSimilarFilmsInfo(filmId: Long): List<FilmSliderInfo>
    fun getById(filmId: Long, mode: String): FilmSliderInfo
}