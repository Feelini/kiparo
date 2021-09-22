package com.soldatov.domain.usecase

import com.soldatov.domain.models.FilmSliderInfo
import com.soldatov.domain.repository.FilmsRepository

class GetSimilarFilmsUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(filmId: Long): List<FilmSliderInfo>{
        return filmsRepository.getSimilarFilmsInfo(filmId)
    }
}