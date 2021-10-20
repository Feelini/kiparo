package com.soldatov.vkino.presentation.di

import com.soldatov.data.api.NetworkService
import com.soldatov.data.repository.FilmsRepositoryImpl
import com.soldatov.domain.repository.FilmsRepository
import com.soldatov.domain.usecase.*
import com.soldatov.domain.usecase.filter.*
import com.soldatov.vkino.presentation.ui.film.FilmFragmentViewModel
import com.soldatov.vkino.presentation.ui.filter.FilterFragmentViewModel
import com.soldatov.vkino.presentation.ui.home.HomeFragmentViewModel
import com.soldatov.vkino.presentation.ui.search.SearchFragmentViewModel
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
    factory { GetFilmsForSearchUseCase(filmsRepository = get()) }
    factory { GetGenresUseCase(filmsRepository = get()) }
    factory { GetYearsUseCase(filmsRepository = get()) }
    factory { GetCategoriesUseCase(filmsRepository = get()) }
    factory { GetCountriesUseCase(filmsRepository = get()) }
    factory { GetActorsUseCase(filmsRepository = get()) }
    factory { GetQualitiesUseCase(filmsRepository = get()) }
    factory { GetOrderByUseCase(filmsRepository = get()) }
}

val appModule = module {
    viewModel {
        HomeFragmentViewModel(
            getTopSliderUseCase = get(),
            getHomeFilmsUseCase = get(),
            getOrderByUseCase = get()
        )
    }
    viewModel { FilmFragmentViewModel(getSimilarFilmsUseCase = get(), getFilmByIdUseCase = get()) }
    viewModel { SearchFragmentViewModel(getFilmsForSearchUseCase = get()) }
    viewModel {
        FilterFragmentViewModel(
            getGenresUseCase = get(),
            getYearsUseCase = get(),
            getCategoriesUseCase = get(),
            getCountriesUseCase = get(),
            getActorsUseCase = get(),
            getQualitiesUseCase = get()
        )
    }
}