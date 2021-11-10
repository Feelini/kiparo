package com.soldatov.domain.usecase.favourite

import com.soldatov.domain.models.favourite.Categories
import com.soldatov.domain.repository.FilmsRepository

class GetFavCatsUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(): List<Categories>{
        return filmsRepository.getFavCats()
    }
}