package com.soldatov.domain.repository

import com.soldatov.domain.models.DomainFilmSliderInfo

interface FilmsRepository {
    suspend fun getTopSliderInfo(): List<DomainFilmSliderInfo>
    suspend fun getSimilarFilmsInfo(filmId: Long): List<DomainFilmSliderInfo>
}