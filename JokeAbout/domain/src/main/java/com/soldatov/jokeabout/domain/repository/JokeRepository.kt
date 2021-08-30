package com.soldatov.jokeabout.domain.repository

import com.soldatov.jokeabout.domain.models.Data

interface JokeRepository {
    fun saveData(data: Data): Boolean
    fun getData(): Data
}