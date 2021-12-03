package com.soldatov.domain.usecase.profile

import com.soldatov.domain.repository.FilmsRepository

class IsLogInUseCase(private val filmsRepository: FilmsRepository) {

    fun execute(): Boolean{
        return filmsRepository.isLogInUser()
    }
}