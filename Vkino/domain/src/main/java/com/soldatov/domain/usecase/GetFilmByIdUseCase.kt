package com.soldatov.domain.usecase

import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.repository.FilmsRepository

class GetFilmByIdUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(filmId: Long, mode: String): FilmInfo{
        return filmsRepository.getById(filmId, mode)
    }
}