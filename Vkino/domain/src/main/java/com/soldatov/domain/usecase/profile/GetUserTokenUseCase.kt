package com.soldatov.domain.usecase.profile

import com.soldatov.domain.repository.FilmsRepository

class GetUserTokenUseCase(private val filmsRepository: FilmsRepository) {

    fun execute(): String{
        return filmsRepository.getUserToken()
    }
}