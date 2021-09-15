package com.soldatov.vkino.presentation.di

import com.soldatov.data.api.NetworkService
import com.soldatov.data.repository.FilmsRepositoryImpl
import com.soldatov.domain.repository.FilmsRepository
import com.soldatov.domain.usecase.GetSimilarFilmsUseCase
import com.soldatov.domain.usecase.GetTopSliderUseCase
import com.soldatov.vkino.presentation.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val domainModule = module {
    single<FilmsRepository> { FilmsRepositoryImpl(NetworkService.apiService) }
    factory { GetTopSliderUseCase(filmsRepository = get()) }
    factory { GetSimilarFilmsUseCase(filmsRepository = get()) }
}

val appModule = module {
    viewModel { MainActivityViewModel(getTopSliderUseCase = get()) }
}