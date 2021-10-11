package com.soldatov.domain.usecase

import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.repository.FilmsRepository

class GetSimilarFilmsUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(filmId: Long): List<FilmInfo>{
        return filmsRepository.getSimilarFilmsInfo(filmId)
    }
}