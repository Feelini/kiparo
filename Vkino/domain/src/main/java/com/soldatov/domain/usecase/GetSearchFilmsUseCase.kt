package com.soldatov.domain.usecase

import com.soldatov.domain.models.SearchData
import com.soldatov.domain.repository.FilmsRepository

class GetSearchFilmsUseCase(private val filmsRepository: FilmsRepository) {
    suspend fun execute(searchQuery: String, page: Int): SearchData {
        return filmsRepository.getSearchFilms(searchQuery, page)
    }
}