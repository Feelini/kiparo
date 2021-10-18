package com.soldatov.domain.usecase.filter

import com.soldatov.domain.models.CountriesList
import com.soldatov.domain.repository.FilmsRepository

class GetCountriesUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(searchQuery: String, page: Int): CountriesList{
        return filmsRepository.getCountries(searchQuery, page)
    }
}