package com.soldatov.covid.domain.usecase

import com.soldatov.covid.domain.models.CovidInfo
import com.soldatov.covid.domain.repository.CovidRepository

class GetCovidInfoUseCase(private val covidRepository: CovidRepository) {

    suspend fun execute(): CovidInfo{
        return covidRepository.getCovidInfo()
    }
}