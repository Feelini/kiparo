package com.soldatov.vkino.presentation.di

import com.soldatov.data.api.NetworkService
import com.soldatov.data.repository.FilmsRepositoryImpl
import com.soldatov.domain.repository.FilmsRepository
import com.soldatov.domain.usecase.*
import com.soldatov.vkino.presentation.viewmodel.FilmFragmentViewModel
import com.soldatov.vkino.presentation.viewmodel.HomeFragmentViewModel
import com.soldatov.vkino.presentation.viewmodel.SearchFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single<FilmsRepository> { FilmsRepositoryImpl(NetworkService.apiService) }
}

val domainModule = module {
    factory { GetTopSliderUseCase(filmsRepository = get()) }
    factory { GetSimilarFilmsUseCase(filmsRepository = get()) }
    factory { GetFilmByIdUseCase(filmsRepository = get()) }
    factory { GetHomeFilmsUseCase(filmsRepository = get()) }
    factory { GetSearchFilmsUseCase(filmsRepository = get()) }
}

val appModule = module {
    viewModel {HomeFragmentViewModel(getTopSliderUseCase = get(), getHomeFilmsUseCase = get())}
    viewModel {FilmFragmentViewModel(getSimilarFilmsUseCase = get(), getFilmByIdUseCase = get())}
    viewModel {SearchFragmentViewModel(getSearchFilmsUseCase = get())}
}