package com.soldatov.jokeabout.domain.usecase

import com.soldatov.jokeabout.domain.repository.JokeRepository

class StartAlarmManagerUseCase(private val jokeRepository: JokeRepository) {
    fun execute(repeatingTime: Long){
        jokeRepository.startAlarm(repeatingTime)
    }
}