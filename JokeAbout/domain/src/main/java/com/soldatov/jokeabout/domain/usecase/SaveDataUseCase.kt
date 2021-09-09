package com.soldatov.jokeabout.domain.usecase

import com.soldatov.jokeabout.domain.models.Data
import com.soldatov.jokeabout.domain.repository.JokeRepository

class SaveDataUseCase(private val jokeRepository: JokeRepository) {
    fun execute(data: Data){
        jokeRepository.saveData(data = data)
    }
}