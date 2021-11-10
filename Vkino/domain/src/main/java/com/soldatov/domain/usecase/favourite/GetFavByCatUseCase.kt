package com.soldatov.domain.usecase.favourite

import com.soldatov.domain.models.favourite.FavouriteResult
import com.soldatov.domain.repository.FilmsRepository

class GetFavByCatUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(catId: Int, page: Int): FavouriteResult{
        return filmsRepository.getFavByCat(catId, page)
    }
}