package com.soldatov.domain.usecase.filter

import com.soldatov.domain.models.ActorsList
import com.soldatov.domain.repository.FilmsRepository

class GetActorsUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(searchQuery: String, page: Int): ActorsList{
        return filmsRepository.getActors(searchQuery, page)
    }
}