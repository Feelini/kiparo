package com.soldatov.covid.presentation.utils

import com.soldatov.covid.data.api.ApiHelper
import com.soldatov.covid.data.api.NetworkService
import com.soldatov.covid.data.repository.CovidRepositoryImpl
import com.soldatov.covid.domain.usecase.GetCovidInfoUseCase

object UseCaseProvider {

    fun provideUserCase(): GetCovidInfoUseCase {
        val apiHelper = ApiHelper(NetworkService.apiService)
        val repository = CovidRepositoryImpl(apiHelper)
        return GetCovidInfoUseCase(repository)
    }
}