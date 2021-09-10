package com.soldatov.covid.domain.usecase

import com.soldatov.covid.domain.models.DomainCovidInfo
import com.soldatov.covid.domain.repository.CovidRepository

class GetCovidInfoUseCase(private val covidRepository: CovidRepository) {

    suspend fun execute(): DomainCovidInfo {
        return covidRepository.getCovidInfo()
    }
}