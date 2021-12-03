package com.soldatov.domain.usecase.profile

import com.soldatov.domain.repository.FilmsRepository

class QuitProfileUseCase(private val filmsRepository: FilmsRepository) {

    fun execute(){
        filmsRepository.quitProfile()
    }
}