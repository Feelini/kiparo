package com.soldatov.jokeabout.data.api

import android.util.Log
import com.soldatov.jokeabout.data.api.deserializer.JokeJsonDeserializer
import com.soldatov.jokeabout.data.api.models.JokeRequestData
import okhttp3.OkHttpClient
import okhttp3.Request

private const val BASE_URL = "http://api.icndb.com/jokes/random?"

class JokeApiImpl : JokeApi {

    private val httpClient = OkHttpClient()
    private val deserializer = JokeJsonDeserializer()

    override fun getJoke(jokeRequestData: JokeRequestData) {
        val request = buildUrl(jokeRequestData)
        httpClient.newCall(request).execute().use {
            if (it.isSuccessful) {
                it.body?.let { responseBody ->
                    val joke = deserializer.getJoke(responseBody.string())
                }
            }
        }
    }

    private fun buildUrl(jokeRequestData: JokeRequestData): Request {
        val urlBuilder = StringBuilder(BASE_URL)
            .append("firstName=").append(jokeRequestData.firstName)
            .append("&")
            .append("lastName=").append(jokeRequestData.lastName)
        return Request.Builder().url(urlBuilder.toString()).build()
    }
}