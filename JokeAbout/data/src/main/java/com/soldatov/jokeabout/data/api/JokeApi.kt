package com.soldatov.jokeabout.data.api

import android.content.Context
import com.soldatov.jokeabout.data.api.models.JokeRequestData

interface JokeApi {
    fun getJoke(context: Context, jokeRequestData: JokeRequestData)
}