package com.soldatov.jokeabout.data.repository

import com.soldatov.jokeabout.data.service.JokeService
import com.soldatov.jokeabout.data.storage.JokeStorage
import com.soldatov.jokeabout.data.storage.models.SharedPrefsData
import com.soldatov.jokeabout.domain.models.Data
import com.soldatov.jokeabout.domain.repository.JokeRepository

class JokeRepositoryImpl(
    private val jokeStorage: JokeStorage,
    private val jokeService: JokeService
) : JokeRepository {

    override fun saveData(data: Data): Boolean {
        return jokeStorage.saveData(data = mapToData(data))
    }

    override fun getData(): Data {
        return mapToDomain(jokeStorage.getData())
    }

    override fun startAlarm(repeatingTime: Long) {
        jokeService.startAlarmManager(repeatingTime)
    }

    private fun mapToData(data: Data): SharedPrefsData {
        return SharedPrefsData(data.timeInMilli, data.firstName, data.lastName)
    }

    private fun mapToDomain(sharedPrefsData: SharedPrefsData): Data {
        return Data(
            sharedPrefsData.timeInMilli,
            sharedPrefsData.firstName,
            sharedPrefsData.lastName
        )
    }
}