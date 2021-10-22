package com.soldatov.domain.usecase.profile

import com.soldatov.domain.repository.FilmsRepository

class SetUserTokenUseCase(private val filmsRepository: FilmsRepository) {

    fun execute(token: String){
        filmsRepository.saveUserToken(token)
    }
}