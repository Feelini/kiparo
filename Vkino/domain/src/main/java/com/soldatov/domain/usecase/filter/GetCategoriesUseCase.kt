package com.soldatov.domain.usecase.filter

import com.soldatov.domain.models.Category
import com.soldatov.domain.repository.FilmsRepository

class GetCategoriesUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(): List<Category>{
        return filmsRepository.getCategories()
    }
}