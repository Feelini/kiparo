package com.soldatov.jokeabout.data.api

import android.content.Context
import com.soldatov.jokeabout.data.api.deserializer.JokeJsonDeserializer
import com.soldatov.jokeabout.data.api.models.JokeRequestData
import okhttp3.OkHttpClient
import okhttp3.Request

private const val BASE_URL = "http://api.icndb.com/jokes/random?"
const val CHANNEL_ID = "uniqueId"
const val NOTIFICATION_ID = 101

class JokeApiImpl : JokeApi {

    private val httpClient = OkHttpClient()
    private val deserializer = JokeJsonDeserializer()
    private lateinit var onGetJoke: OnGetJoke

    interface OnGetJoke{
        fun onGetJoke(contentText: String)
    }

    override fun getJoke(context: Context, jokeRequestData: JokeRequestData) {
        val request = buildUrl(jokeRequestData)
        httpClient.newCall(request).execute().use {
            if (it.isSuccessful) {
                it.body?.let { responseBody ->
                    val joke = deserializer.getJoke(responseBody.string())
                    joke?.let {
                        onGetJoke = context as OnGetJoke
                        onGetJoke.onGetJoke(joke.value.joke)
                    }
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