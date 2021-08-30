package com.soldatov.jokeabout.data.repository

import com.soldatov.jokeabout.data.storage.JokeStorage
import com.soldatov.jokeabout.data.storage.models.SharedPrefsData
import com.soldatov.jokeabout.domain.models.Data
import com.soldatov.jokeabout.domain.repository.JokeRepository

class JokeRepositoryImpl(private val jokeStorage: JokeStorage) : JokeRepository {

    override fun saveData(data: Data): Boolean {
        return jokeStorage.saveData(data = mapToData(data))
    }

    override fun getData(): Data {
        return mapToDomain(jokeStorage.getData())
    }

    private fun mapToData(data: Data): SharedPrefsData{
        return SharedPrefsData(data.timeInSec, data.firstName, data.lastName)
    }

    private fun mapToDomain(sharedPrefsData: SharedPrefsData): Data{
        return Data(sharedPrefsData.timeInSec, sharedPrefsData.firstName, sharedPrefsData.lastName)
    }
}