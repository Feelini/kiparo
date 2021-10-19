package com.soldatov.domain.usecase.filter

import com.soldatov.domain.models.Quality
import com.soldatov.domain.repository.FilmsRepository

class GetQualitiesUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(): List<Quality>{
        return filmsRepository.getQualities()
    }
}