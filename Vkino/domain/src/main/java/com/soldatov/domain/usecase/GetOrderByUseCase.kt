package com.soldatov.domain.usecase

import com.soldatov.domain.repository.FilmsRepository

class GetOrderByUseCase(private val filmsRepository: FilmsRepository) {

    fun execute(): List<String>{
        return filmsRepository.getOrderByData()
    }
}