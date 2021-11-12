package com.soldatov.domain.usecase.favourite

import com.soldatov.domain.repository.FilmsRepository

class AddFavoriteUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(filmId: Long): Boolean{
        return filmsRepository.addFavourite(filmId)
    }
}