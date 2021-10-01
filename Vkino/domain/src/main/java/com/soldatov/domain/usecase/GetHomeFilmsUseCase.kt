package com.soldatov.domain.usecase

import com.soldatov.domain.models.FilmSliderInfo
import com.soldatov.domain.repository.FilmsRepository

class GetHomeFilmsUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(): List<FilmSliderInfo> {
        return filmsRepository.getHomePageFilms()
    }
}