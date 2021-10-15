package com.soldatov.domain.usecase.filter

import com.soldatov.domain.models.Years
import com.soldatov.domain.repository.FilmsRepository

class GetYearsUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(): Years{
        return filmsRepository.getYears()
    }
}