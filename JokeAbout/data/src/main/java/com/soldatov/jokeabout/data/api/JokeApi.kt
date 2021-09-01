package com.soldatov.jokeabout.data.api

import com.soldatov.jokeabout.data.api.models.JokeRequestData

interface JokeApi {
    fun getJoke(jokeRequestData: JokeRequestData)
}