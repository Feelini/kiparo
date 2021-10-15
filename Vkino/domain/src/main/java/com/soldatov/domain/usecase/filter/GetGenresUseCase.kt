package com.soldatov.domain.usecase.filter

import com.soldatov.domain.models.GenresList
import com.soldatov.domain.repository.FilmsRepository

class GetGenresUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(): GenresList{
        return filmsRepository.getGenres()
    }
}