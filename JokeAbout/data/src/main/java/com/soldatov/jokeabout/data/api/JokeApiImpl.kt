package com.soldatov.jokeabout.data.api

import android.content.Context
import com.soldatov.jokeabout.data.api.deserializer.JokeJsonDeserializer
import com.soldatov.jokeabout.data.api.models.Joke
import com.soldatov.jokeabout.data.api.models.JokeRequestData
import okhttp3.OkHttpClient
import okhttp3.Request

private const val BASE_URL = "http://api.icndb.com/jokes/random?"

class JokeApiImpl : JokeApi {

    private val httpClient = OkHttpClient()
    private val deserializer = JokeJsonDeserializer()

    override fun getJoke(context: Context, jokeRequestData: JokeRequestData): Joke? {
        val request = buildUrl(jokeRequestData)
        httpClient.newCall(request).execute().use {
            if (it.isSuccessful) {
                it.body?.let { responseBody ->
                    return deserializer.getJoke(responseBody.string())
                }
            }
        }
        return null
    }

    private fun buildUrl(jokeRequestData: JokeRequestData): Request {
        val urlBuilder = StringBuilder(BASE_URL)
            .append("firstName=").append(jokeRequestData.firstName)
            .append("&")
            .append("lastName=").append(jokeRequestData.lastName)
        return Request.Builder().url(urlBuilder.toString()).build()
    }
}