package com.soldatov.domain.usecase

import com.soldatov.domain.models.DomainFilmSliderInfo
import com.soldatov.domain.repository.FilmsRepository

class GetSimilarFilmsUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(filmId: Long): List<DomainFilmSliderInfo>{
        return filmsRepository.getSimilarFilmsInfo(filmId)
    }
}