package com.soldatov.domain.usecase

import com.soldatov.domain.models.DomainTopSliderInfo
import com.soldatov.domain.repository.FilmsRepository

class GetTopSliderUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(): List<DomainTopSliderInfo>{
        return filmsRepository.getTopSliderInfo()
    }
}