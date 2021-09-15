package com.soldatov.domain.usecase

import com.soldatov.domain.models.DomainFilmSliderInfo
import com.soldatov.domain.repository.FilmsRepository

class GetTopSliderUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(): List<DomainFilmSliderInfo>{
        return filmsRepository.getTopSliderInfo()
    }
}