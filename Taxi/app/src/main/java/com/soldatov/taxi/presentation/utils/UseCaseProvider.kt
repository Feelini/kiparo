package com.soldatov.taxi.presentation.utils

import com.soldatov.data.api.NetworkService
import com.soldatov.data.repository.TaxiRepositoryImpl
import com.soldatov.domain.usecase.GetTaxiInfoUseCase

object UseCaseProvider {

    fun provideGetTaxiInfoUseCase(): GetTaxiInfoUseCase{
        val repository = TaxiRepositoryImpl(NetworkService.apiService)
        return GetTaxiInfoUseCase(repository)
    }
}