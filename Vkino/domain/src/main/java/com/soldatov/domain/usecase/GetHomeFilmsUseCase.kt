package com.soldatov.domain.usecase

import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.repository.FilmsRepository

class GetHomeFilmsUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(): List<FilmInfo> {
        return filmsRepository.getHomePageFilms()
    }
}