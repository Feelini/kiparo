package com.soldatov.jokeabout.data.storage

import com.soldatov.jokeabout.data.storage.models.SharedPrefsData

interface JokeStorage {
    fun saveData(data: SharedPrefsData): Boolean
    fun getData(): SharedPrefsData
}