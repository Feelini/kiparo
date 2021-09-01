package com.soldatov.jokeabout.data.api

import com.soldatov.jokeabout.data.api.models.JokeRequestData
import okhttp3.OkHttpClient
import okhttp3.Request

private const val BASE_URL = "http://api.icndb.com/jokes/random?"

class JokeApiImpl : JokeApi {

    private val httpClient = OkHttpClient()

    override fun getJoke(jokeRequestData: JokeRequestData) {
        val request = buildUrl(jokeRequestData)
        httpClient.newCall(request).execute().use {
            print(it.message)
            if (it.isSuccessful){
                print(it.body)
            }
        }
    }

    private fun buildUrl(jokeRequestData: JokeRequestData): Request {
        val urlBuilder = StringBuilder(BASE_URL)
            .append("firstName=").append(jokeRequestData.firstName)
            .append("&")
            .append("last_name=").append(jokeRequestData.lastName)
        return Request.Builder().url(urlBuilder.toString()).build()
    }
}