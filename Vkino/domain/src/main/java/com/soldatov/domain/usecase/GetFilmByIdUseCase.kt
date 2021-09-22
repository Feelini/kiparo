package com.soldatov.domain.usecase

import com.soldatov.domain.models.FilmSliderInfo
import com.soldatov.domain.repository.FilmsRepository

class GetFilmByIdUseCase(private val filmsRepository: FilmsRepository) {

    fun execute(filmId: Long): FilmSliderInfo{
        return filmsRepository.getFilmById(filmId)
    }
}