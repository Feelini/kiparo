package com.soldatov.covid.presentation.di

import com.soldatov.covid.data.api.ApiHelper
import com.soldatov.covid.data.api.NetworkService
import com.soldatov.covid.data.repository.CovidRepositoryImpl
import com.soldatov.covid.domain.repository.CovidRepository
import com.soldatov.covid.domain.usecase.GetCovidInfoUseCase
import com.soldatov.covid.presentation.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiHelper(NetworkService.apiService) }
    single<CovidRepository> { CovidRepositoryImpl(get()) }
    factory { GetCovidInfoUseCase(get()) }
    viewModel { MainActivityViewModel(get()) }
}