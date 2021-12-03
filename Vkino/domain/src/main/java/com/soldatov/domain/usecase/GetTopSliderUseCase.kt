package com.soldatov.domain.usecase

import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.repository.FilmsRepository

class GetTopSliderUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(): List<FilmInfo>{
        return filmsRepository.getTopSliderInfo()
    }
}