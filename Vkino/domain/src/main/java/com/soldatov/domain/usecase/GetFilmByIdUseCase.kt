package com.soldatov.domain.usecase

import com.soldatov.domain.models.DomainFilmSliderInfo
import com.soldatov.domain.repository.FilmsRepository

class GetFilmByIdUseCase(private val filmsRepository: FilmsRepository) {

    fun execute(filmId: Long): DomainFilmSliderInfo{
        return filmsRepository.getFilmById(filmId)
    }
}