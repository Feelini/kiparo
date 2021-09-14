package com.soldatov.domain.usecase

import com.soldatov.domain.models.DomainTaxiInfo
import com.soldatov.domain.repository.TaxiRepository

class GetTaxiInfoUseCase(private val taxiRepository: TaxiRepository) {

    suspend fun execute(): List<DomainTaxiInfo>{
        return taxiRepository.getTaxiInfo()
    }
}