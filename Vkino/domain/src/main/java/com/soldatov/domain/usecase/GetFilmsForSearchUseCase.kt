package com.soldatov.domain.usecase

import com.soldatov.domain.models.FilmsList
import com.soldatov.domain.repository.FilmsRepository

class GetFilmsForSearchUseCase(private val filmsRepository: FilmsRepository) {
    suspend fun execute(searchQuery: String, page: Int): FilmsList {
        return filmsRepository.getSearchFilms(searchQuery, page)
    }
}