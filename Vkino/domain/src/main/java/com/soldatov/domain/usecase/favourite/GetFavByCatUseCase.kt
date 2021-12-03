package com.soldatov.domain.usecase.favourite

import com.soldatov.domain.models.favorite.FavoriteResult
import com.soldatov.domain.repository.FilmsRepository

class GetFavByCatUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(catId: Int, page: Int): FavoriteResult{
        return filmsRepository.getFavByCat(catId, page)
    }
}