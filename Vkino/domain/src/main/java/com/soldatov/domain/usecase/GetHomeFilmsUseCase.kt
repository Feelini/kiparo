package com.soldatov.domain.usecase

import com.soldatov.domain.models.FilmsList
import com.soldatov.domain.models.FilterParams
import com.soldatov.domain.repository.FilmsRepository

class GetHomeFilmsUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(filterParams: FilterParams): FilmsList {
        return filmsRepository.getHomePageFilms(filterParams)
    }
}